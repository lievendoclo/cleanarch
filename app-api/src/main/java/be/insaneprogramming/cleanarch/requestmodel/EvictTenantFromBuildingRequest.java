package be.insaneprogramming.cleanarch.requestmodel;

public class EvictTenantFromBuildingRequest {
	private String buildingId;
	private String tenantId;

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