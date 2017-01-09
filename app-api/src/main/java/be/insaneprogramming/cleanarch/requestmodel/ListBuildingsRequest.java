package be.insaneprogramming.cleanarch.requestmodel;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class ListBuildingsRequest {
	private final String nameStartsWith;

	private ListBuildingsRequest(@Nullable String nameStartsWith) {
		this.nameStartsWith = nameStartsWith;
	}

	public Optional<String> getNameStartsWith() {
		return Optional.ofNullable(nameStartsWith);
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

	public static class Builder {
		private String nameStartsWith;

		public Builder nameStartsWith(@Nonnull String value) {
			this.nameStartsWith = value;
			return this;
		}

		public ListBuildingsRequest build() {
			return new ListBuildingsRequest(nameStartsWith);
		}
	}
}
