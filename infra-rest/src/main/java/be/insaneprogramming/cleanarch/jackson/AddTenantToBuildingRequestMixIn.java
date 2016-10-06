package be.insaneprogramming.cleanarch.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;

public class AddTenantToBuildingRequestMixIn extends AddTenantToBuildingRequest {
	@JsonCreator
	public AddTenantToBuildingRequestMixIn(@JsonProperty("buildingId") String buildingId, @JsonProperty("name") String name) {
		super(buildingId, name);
	}
}
