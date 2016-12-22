package be.insaneprogramming.cleanarch.requestmodel;

import java.util.Objects;

import jdk.nashorn.internal.ir.annotations.Immutable;

@Immutable
public class CreateBuildingRequest {
	private final String name;

	public CreateBuildingRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CreateBuildingRequest that = (CreateBuildingRequest) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
