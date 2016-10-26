package be.insaneprogramming.cleanarch.requestmodel;

public final class CreateBuildingRequest {
	private final String name;

	public CreateBuildingRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
