package be.insaneprogramming.cleanarch.presenter.payloadmodel;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequestBuilder;

@Value.Immutable
@JsonDeserialize
public interface CreateBuildingJsonPayload {
	String getName();

	default CreateBuildingRequest toRequest() {
		return CreateBuildingRequestBuilder.builder().name(getName()).build();
	}
}
