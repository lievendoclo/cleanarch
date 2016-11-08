package be.insaneprogramming.cleanarch.entitygatewayimpl

import be.insaneprogramming.cleanarch.entity.*
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntity
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntityRepository
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.TenantJpaEntity
import java.util.*

class JpaBuildingEntityGateway(private val buildingJpaEntityRepository: BuildingJpaEntityRepository, private val buildingFactory: BuildingFactory, private val tenantFactory: TenantFactory) : BuildingEntityGateway {

    override fun save(building: Building): String {
        return buildingJpaEntityRepository.save(fromDomain(building)).id
    }

    override fun findAll(): List<Building> {
        val buildings = ArrayList<Building>()
        buildingJpaEntityRepository.findAll().forEach { it -> buildings.add(toDomain(it)) }
        return buildings
    }

    override fun findById(id: BuildingId): Building {
        return toDomain(buildingJpaEntityRepository.findOne(id))
    }

    private fun fromDomain(building: Building): BuildingJpaEntity {
        return BuildingJpaEntity(
                building.id,
                building.name,
                building.tenants.map { fromDomain(it) }
        )
    }

    private fun toDomain(entity: BuildingJpaEntity): Building {
        val building = buildingFactory.createBuilding(entity.id, entity.name)
        entity.tenants.map { toDomain(it) }.forEach { building.addTenant(it) }
        return building
    }

    private fun fromDomain(tenant: Tenant): TenantJpaEntity {
        return TenantJpaEntity(
                tenant.id,
                tenant.name
        )
    }

    private fun toDomain(entity: TenantJpaEntity): Tenant {
        return tenantFactory.createTenant(entity.id, entity.name)
    }
}
