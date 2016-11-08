package be.insaneprogramming.cleanarch.rest.payloadmodel

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateBuildingJsonPayload(@JsonProperty("name") val name: String) {
}
