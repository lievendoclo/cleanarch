package be.insaneprogramming.cleanarch.interactor

import be.insaneprogramming.cleanarch.boundary.CreateBuilding
import be.insaneprogramming.cleanarch.entity.BuildingFactory
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest

class CreateBuildingImpl(private val buildingEntityGateway: BuildingEntityGateway, private val buildingFactory: BuildingFactory) : CreateBuilding {

    override fun execute(request: CreateBuildingRequest): String {
        val building = buildingFactory.createBuilding(request.name)
        return buildingEntityGateway.save(building)
    }
}
