package be.insaneprogramming.cleanarch.boundary

import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest

@FunctionalInterface
interface AddTenantToBuilding {
    fun execute(request: AddTenantToBuildingRequest): String
}
