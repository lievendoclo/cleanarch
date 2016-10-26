package be.insaneprogramming.cleanarch.entity;

import java.util.Objects;

public final class BuildingId {
	private final String value;

	private BuildingId(String value) {
		this.value = value;
	}

	public static BuildingId of(String value) {
		return new BuildingId(value);
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BuildingId that = (BuildingId) o;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
