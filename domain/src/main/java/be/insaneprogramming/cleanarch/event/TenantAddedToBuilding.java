package be.insaneprogramming.cleanarch.event;

public class TenantAddedToBuilding {
	private final String buildingId;
	private final String tenantId;
	private final String name;

	public TenantAddedToBuilding(String buildingId, String tenantId, String name) {
		this.buildingId = buildingId;
		this.tenantId = tenantId;
		this.name = name;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getName() {
		return name;
	}
}
