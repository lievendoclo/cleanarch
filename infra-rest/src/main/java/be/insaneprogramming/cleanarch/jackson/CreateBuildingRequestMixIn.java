package be.insaneprogramming.cleanarch.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;

public class CreateBuildingRequestMixIn extends CreateBuildingRequest {
	@JsonCreator
	public CreateBuildingRequestMixIn(@JsonProperty("name") String name) {
		super(name);
	}
}
