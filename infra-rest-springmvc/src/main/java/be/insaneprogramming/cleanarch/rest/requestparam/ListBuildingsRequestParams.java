package be.insaneprogramming.cleanarch.rest.requestparam;

import java.util.Optional;

import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;

public class ListBuildingsRequestParams {
	private String nameStartsWith;

	private Optional<String> getNameStartsWith() {
		return Optional.ofNullable(nameStartsWith);
	}

	public void setNameStartsWith(String nameStartsWith) {
		this.nameStartsWith = nameStartsWith;
	}

	public ListBuildingsRequest toRequest() {
		final ListBuildingsRequest.Builder builder = new ListBuildingsRequest.Builder();
		getNameStartsWith().ifPresent(builder::nameStartsWith);
		return builder.build();
	}
}
