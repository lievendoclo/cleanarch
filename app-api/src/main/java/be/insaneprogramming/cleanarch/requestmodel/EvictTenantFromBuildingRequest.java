package be.insaneprogramming.cleanarch.requestmodel;

public final class EvictTenantFromBuildingRequest {
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
}