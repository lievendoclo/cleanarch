package be.insaneprogramming.cleanarch.event;

public class TenantEvictedFromBuilding {
	private final String buildingId;
	private final String tenantId;

	public TenantEvictedFromBuilding(String buildingId, String tenantId) {
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
