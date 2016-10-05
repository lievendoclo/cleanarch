package be.insaneprogramming.cleanarch.requestmodel;

public class CreateBuildingRequest {
	private String name;

	public CreateBuildingRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
