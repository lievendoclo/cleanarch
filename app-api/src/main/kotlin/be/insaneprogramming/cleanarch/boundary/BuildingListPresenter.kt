package be.insaneprogramming.cleanarch.boundary

import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel

interface BuildingListPresenter<T> {
    fun present(buildingResponses: List<BuildingResponseModel>): T
}
