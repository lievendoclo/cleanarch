package be.insaneprogramming.cleanarch.rest.requestparam;

import java.util.Optional;

import javax.ws.rs.QueryParam;

import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;

public class ListBuildingRequestParams {
	@QueryParam("nameStartsWith")
	String nameStartsWith;

	private Optional<String> getNameStartsWith() {
		return Optional.ofNullable(nameStartsWith);
	}

	public ListBuildingsRequest toRequest() {
		final ListBuildingsRequest.Builder builder = new ListBuildingsRequest.Builder();
		getNameStartsWith().ifPresent(builder::nameStartsWith);
		return builder.build();
	}
}
