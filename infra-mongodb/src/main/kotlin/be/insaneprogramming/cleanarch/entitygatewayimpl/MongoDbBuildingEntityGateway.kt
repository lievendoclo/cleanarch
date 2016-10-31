package be.insaneprogramming.cleanarch.entitygatewayimpl

import be.insaneprogramming.cleanarch.entity.Building
import be.insaneprogramming.cleanarch.entity.BuildingId
import be.insaneprogramming.cleanarch.entity.Tenant
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.entitygatewayimpl.morphia.BuildingDocument
import be.insaneprogramming.cleanarch.entitygatewayimpl.morphia.TenantDocument
import org.mongodb.morphia.Datastore

class MongoDbBuildingEntityGateway(val dataStore: Datastore) : BuildingEntityGateway {

    override fun save(building: Building): String {
        dataStore.save(toDocument(building))
        return building.id
    }

    override fun findAll(): List<Building> {
        return dataStore.find(BuildingDocument::class.java).map { toDomain(it) }.toList()
    }

    override fun findById(id: BuildingId): Building {
        return toDomain(dataStore.get(BuildingDocument::class.java, id))
    }

    fun toDomain(building: BuildingDocument): Building {
        return Building(building.id, building.name, building.tenants.map { toDomain(it) }.toMutableList())
    }

    fun toDomain(tenant: TenantDocument): Tenant {
        return Tenant(tenant.id, tenant.name)
    }

    fun toDocument(building: Building): BuildingDocument {
        return BuildingDocument(building.id, building.name, building.tenants.map { toDocument(it) })
    }

    fun toDocument(tenant: Tenant): TenantDocument {
        return TenantDocument(tenant.id, tenant.name)
    }
}
