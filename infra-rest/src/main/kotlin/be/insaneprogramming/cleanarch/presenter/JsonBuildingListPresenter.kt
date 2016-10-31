package be.insaneprogramming.cleanarch.presenter

import java.util.stream.Collectors

import be.insaneprogramming.cleanarch.boundary.BuildingListPresenter
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel
import be.insaneprogramming.cleanarch.rest.viewmodel.BuildingJson
import be.insaneprogramming.cleanarch.rest.viewmodel.TenantJson

class JsonBuildingListPresenter : BuildingListPresenter<List<BuildingJson>> {
    override fun present(buildingResponses: List<BuildingResponseModel>): List<BuildingJson> {
        return buildingResponses
                .map { it ->
                    val tenants = it.tenants.stream().map({ t -> TenantJson(t.id, t.name) }).collect(Collectors.toList<TenantJson>())
                    BuildingJson(it.id, it.name, tenants)
                }
    }
}
