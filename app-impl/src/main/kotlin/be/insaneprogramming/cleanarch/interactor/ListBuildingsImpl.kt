package be.insaneprogramming.cleanarch.interactor

import be.insaneprogramming.cleanarch.boundary.BuildingListPresenter
import be.insaneprogramming.cleanarch.boundary.ListBuildings
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel
import be.insaneprogramming.cleanarch.responsemodel.TenantResponseModel
import java.util.concurrent.CompletableFuture

class ListBuildingsImpl(private val buildingEntityGateway: BuildingEntityGateway) : ListBuildings {

    override fun <T> execute(request: ListBuildingsRequest, buildingListPresenter: BuildingListPresenter<T>): CompletableFuture<T> {
        return CompletableFuture.supplyAsync {
            buildingListPresenter.present(buildingEntityGateway.findAll().map { b ->
                val tenantResponseModels = b.tenants.map { TenantResponseModel(it.id.value, it.name) }
                BuildingResponseModel(b.id.value, b.name, tenantResponseModels)
            })
        }
    }
}
