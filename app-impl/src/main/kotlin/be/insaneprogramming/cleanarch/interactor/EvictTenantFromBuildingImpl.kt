package be.insaneprogramming.cleanarch.interactor

import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding
import be.insaneprogramming.cleanarch.entity.BuildingId
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest

class EvictTenantFromBuildingImpl(private val buildingEntityGateway: BuildingEntityGateway) : EvictTenantFromBuilding {
    override fun execute(request: EvictTenantFromBuildingRequest) {
        val building = buildingEntityGateway.findById(BuildingId(request.buildingId))
        building.evictTenant(request.tenantId)
        buildingEntityGateway.save(building)
    }
}
