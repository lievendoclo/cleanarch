package be.insaneprogramming.cleanarch.requestmodel;

import java.util.Objects;

import javax.annotation.concurrent.Immutable;

@Immutable
public class AddTenantToBuildingRequest {
	private final String buildingId;
	private final String tenantName;

	public AddTenantToBuildingRequest(String buildingId, String tenantName) {
		this.buildingId = buildingId;
		this.tenantName = tenantName;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public String getTenantName() {
		return tenantName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AddTenantToBuildingRequest that = (AddTenantToBuildingRequest) o;
		return Objects.equals(buildingId, that.buildingId) &&
				Objects.equals(tenantName, that.tenantName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(buildingId, tenantName);
	}
}
