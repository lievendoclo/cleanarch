package be.insaneprogramming.cleanarch.rest.payloadmodel

import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest
import com.fasterxml.jackson.annotation.JsonProperty

class AddTenantToBuildingJsonPayload(@JsonProperty("name") val name: String) {

    fun toRequest(buildingId: String): AddTenantToBuildingRequest {
        return AddTenantToBuildingRequest(buildingId, name)
    }
}
