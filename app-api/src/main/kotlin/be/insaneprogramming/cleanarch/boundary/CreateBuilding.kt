package be.insaneprogramming.cleanarch.boundary

import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest

@FunctionalInterface
interface CreateBuilding {
    fun execute(request: CreateBuildingRequest): String
}
