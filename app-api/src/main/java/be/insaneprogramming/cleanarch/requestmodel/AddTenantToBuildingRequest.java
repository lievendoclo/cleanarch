package be.insaneprogramming.cleanarch.requestmodel;

public class AddTenantToBuildingRequest {
	private String buildingId;
	private String name;

	public AddTenantToBuildingRequest(String buildingId, String name) {
		this.buildingId = buildingId;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getBuildingId() {
		return buildingId;
	}
}
