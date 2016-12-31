package be.insaneprogramming.cleanarch.rest.payloadmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateBuildingJsonPayload {
	private final String name;

	@JsonCreator
	public CreateBuildingJsonPayload(@JsonProperty("name") String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
