package be.insaneprogramming.cleanarch.interactor

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding
import be.insaneprogramming.cleanarch.entity.TenantFactory
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest

class AddTenantToBuildingImpl(private val buildingEntityGateway: BuildingEntityGateway, private val tenantFactory: TenantFactory) : AddTenantToBuilding {
    override fun execute(request: AddTenantToBuildingRequest): String {
        val building = buildingEntityGateway.findById(request.buildingId)
        val tenant = tenantFactory.createTenant(request.name)
        building.addTenant(tenant)
        buildingEntityGateway.save(building)
        return tenant.id
    }
}