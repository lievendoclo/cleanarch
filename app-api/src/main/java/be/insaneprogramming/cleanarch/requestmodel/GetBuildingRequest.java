package be.insaneprogramming.cleanarch.requestmodel;

import java.util.Objects;

import javax.annotation.concurrent.Immutable;

@Immutable
public class GetBuildingRequest {
	private final String buildingId;

	public GetBuildingRequest(String buildingId) {
		this.buildingId = buildingId;
	}

	public String getBuildingId() {
		return buildingId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GetBuildingRequest that = (GetBuildingRequest) o;
		return Objects.equals(buildingId, that.buildingId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(buildingId);
	}
}
