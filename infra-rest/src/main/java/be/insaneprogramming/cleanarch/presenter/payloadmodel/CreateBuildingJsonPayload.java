package be.insaneprogramming.cleanarch.presenter.payloadmodel;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.ImmutableCreateBuildingRequest;

@Value.Immutable
@JsonDeserialize
public interface CreateBuildingJsonPayload {
	String getName();

	default CreateBuildingRequest toRequest() {
		return ImmutableCreateBuildingRequest.builder().name(getName()).build();
	}
}
