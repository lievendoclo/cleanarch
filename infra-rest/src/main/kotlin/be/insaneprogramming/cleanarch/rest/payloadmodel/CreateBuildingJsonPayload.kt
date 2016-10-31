package be.insaneprogramming.cleanarch.rest.payloadmodel

import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest
import com.fasterxml.jackson.annotation.JsonProperty

class CreateBuildingJsonPayload(@JsonProperty("name") val name: String) {

    fun toRequest(): CreateBuildingRequest {
        return CreateBuildingRequest(name)
    }
}
