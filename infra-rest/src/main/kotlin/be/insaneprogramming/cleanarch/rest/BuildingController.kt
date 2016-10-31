package be.insaneprogramming.cleanarch.rest

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding
import be.insaneprogramming.cleanarch.boundary.CreateBuilding
import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding
import be.insaneprogramming.cleanarch.boundary.ListBuildings
import be.insaneprogramming.cleanarch.presenter.JsonBuildingListPresenter
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest
import be.insaneprogramming.cleanarch.rest.BuildingController.Companion.RESOURCE_URI_TEMPLATE
import be.insaneprogramming.cleanarch.rest.payloadmodel.AddTenantToBuildingJsonPayload
import be.insaneprogramming.cleanarch.rest.payloadmodel.CreateBuildingJsonPayload
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.async.DeferredResult
import org.springframework.web.util.UriTemplate

@RestController
@RequestMapping(RESOURCE_URI_TEMPLATE)
class BuildingController(
        @Autowired val listBuildings: ListBuildings,
        @Autowired val createBuilding: CreateBuilding,
        @Autowired val addTenantToBuilding: AddTenantToBuilding,
        @Autowired val evictTenantFromBuilding: EvictTenantFromBuilding
    ) {

    companion object {
        const val RESOURCE_URI_TEMPLATE = "/building"
        const val GET_SINGLE_BUILDING_URI_TEMPLATE = RESOURCE_URI_TEMPLATE + "/{buildingId}"
        const val GET_BUILDING_TENANT_URI_TEMPLATE = GET_SINGLE_BUILDING_URI_TEMPLATE + "/tenant/{tenantId}"
    }

    @PostMapping
    fun create(@RequestBody payload: CreateBuildingJsonPayload): ResponseEntity<*> {
        val id = createBuilding.execute(payload.toRequest())
        return ResponseEntity.created(UriTemplate(GET_SINGLE_BUILDING_URI_TEMPLATE).expand(id).normalize()).build()
    }

    @GetMapping
    fun list(): DeferredResult<ResponseEntity<*>> {
        val deferred = DeferredResult<ResponseEntity<*>>()
        val responseModels = listBuildings.execute(ListBuildingsRequest(), JsonBuildingListPresenter())
        responseModels.whenComplete { buildingJsons, throwable ->
            if (throwable != null) {
                deferred.setResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body<String>(throwable.message))
            } else {
                deferred.setResult(ResponseEntity.ok(buildingJsons))
            }
        }
        return deferred
    }

    @PostMapping("{buildingId}/tenant")
    fun addTenant(@PathVariable("buildingId") buildingId: String, @RequestBody payload: AddTenantToBuildingJsonPayload): ResponseEntity<*> {
        val id = addTenantToBuilding.execute(payload.toRequest(buildingId))
        return ResponseEntity.created(UriTemplate(GET_BUILDING_TENANT_URI_TEMPLATE).expand(buildingId, id).normalize()).build()
    }

    @DeleteMapping("{buildingId}/tenant/{tenantId}")
    fun evictTenant(@PathVariable("buildingId") buildingId: String, @PathVariable("tenantId") tenantId: String) {
        val request = EvictTenantFromBuildingRequest(buildingId, tenantId)
        evictTenantFromBuilding.execute(request)
    }
}
