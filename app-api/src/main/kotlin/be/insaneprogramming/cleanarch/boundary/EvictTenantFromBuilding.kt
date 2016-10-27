package be.insaneprogramming.cleanarch.boundary

import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest

@FunctionalInterface
interface EvictTenantFromBuilding {
    fun execute(request: EvictTenantFromBuildingRequest)
}
