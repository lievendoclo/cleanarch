package be.insaneprogramming.cleanarch.rest.payloadmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;

public final class AddTenantToBuildingJsonPayload {
	private final String name;

	@JsonCreator
	public AddTenantToBuildingJsonPayload(@JsonProperty("name") String name) {
		this.name = name;
	}

	public AddTenantToBuildingRequest toRequest(String buildingId) {
		return new AddTenantToBuildingRequest(buildingId, name);
	}
}
