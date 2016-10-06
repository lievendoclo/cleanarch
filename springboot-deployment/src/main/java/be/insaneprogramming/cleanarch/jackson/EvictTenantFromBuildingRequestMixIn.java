package be.insaneprogramming.cleanarch.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest;

public class EvictTenantFromBuildingRequestMixIn extends EvictTenantFromBuildingRequest {
	@JsonCreator
	public EvictTenantFromBuildingRequestMixIn(@JsonProperty("buildingId") String buildingId, @JsonProperty("name") String tenantId) {
		super(buildingId, tenantId);
	}
}
