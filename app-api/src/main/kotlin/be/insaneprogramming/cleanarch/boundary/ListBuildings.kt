package be.insaneprogramming.cleanarch.boundary

import java.util.concurrent.CompletableFuture

import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest

@FunctionalInterface
interface ListBuildings {
    fun <T> execute(request: ListBuildingsRequest, buildingListPresenter: BuildingListPresenter<T>): CompletableFuture<T>
}
