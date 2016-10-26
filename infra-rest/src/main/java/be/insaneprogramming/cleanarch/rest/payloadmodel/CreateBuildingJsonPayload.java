package be.insaneprogramming.cleanarch.rest.payloadmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;

public final class CreateBuildingJsonPayload {
	private final String name;

	@JsonCreator
	public CreateBuildingJsonPayload(@JsonProperty("name") String name) {
		this.name = name;
	}

	public CreateBuildingRequest toRequest() {
		return new CreateBuildingRequest(name);
	}
}
