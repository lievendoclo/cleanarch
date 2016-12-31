package be.insaneprogramming.cleanarch.rest.payloadmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddTenantToBuildingJsonPayload {
	private final String name;

	@JsonCreator
	public AddTenantToBuildingJsonPayload(@JsonProperty("name") String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
