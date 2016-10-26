package be.insaneprogramming.cleanarch.requestmodel;

public final class AddTenantToBuildingRequest {
	private final String buildingId;
	private final String name;

	public AddTenantToBuildingRequest(String buildingId, String name) {
		this.buildingId = buildingId;
		this.name = name;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public String getName() {
		return name;
	}
}
