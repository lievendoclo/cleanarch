package be.insaneprogramming.cleanarch.requestmodel;

import java.util.Objects;

import javax.annotation.concurrent.Immutable;

@Immutable
public class ListBuildingsRequest {
	private final String nameStartsWith;

	public ListBuildingsRequest(String nameStartsWith) {
		this.nameStartsWith = nameStartsWith;
	}

	public String getNameStartsWith() {
		return nameStartsWith;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ListBuildingsRequest that = (ListBuildingsRequest) o;
		return Objects.equals(nameStartsWith, that.nameStartsWith);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nameStartsWith);
	}
}
