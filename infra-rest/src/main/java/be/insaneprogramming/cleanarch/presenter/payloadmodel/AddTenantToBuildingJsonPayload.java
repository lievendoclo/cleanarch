package be.insaneprogramming.cleanarch.presenter.payloadmodel;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.ImmutableAddTenantToBuildingRequest;

@Value.Immutable
@JsonDeserialize
public interface AddTenantToBuildingJsonPayload {
	String getName();

	default AddTenantToBuildingRequest toRequest(String buildingId) {
		return ImmutableAddTenantToBuildingRequest.builder().buildingId(buildingId).name(getName()).build();
	}
}
