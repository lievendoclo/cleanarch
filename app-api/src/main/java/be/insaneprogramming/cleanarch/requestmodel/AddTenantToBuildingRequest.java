package be.insaneprogramming.cleanarch.requestmodel;

public class AddTenantToBuildingRequest {
	private String buildingId;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
}
