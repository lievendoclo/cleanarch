package be.insaneprogramming.cleanarch.requestmodel;

import java.util.Objects;

import javax.annotation.concurrent.Immutable;

@Immutable
public class EvictTenantFromBuildingRequest {
	private final String buildingId;
	private final String tenantId;

	public EvictTenantFromBuildingRequest(String buildingId, String tenantId) {
		this.buildingId = buildingId;
		this.tenantId = tenantId;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public String getTenantId() {
		return tenantId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EvictTenantFromBuildingRequest that = (EvictTenantFromBuildingRequest) o;
		return Objects.equals(buildingId, that.buildingId) &&
				Objects.equals(tenantId, that.tenantId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(buildingId, tenantId);
	}
}
