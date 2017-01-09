package be.insaneprogramming.cleanarch.event;

public class BuildingCreated {
	private final String id;
	private final String name;

	public BuildingCreated(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
