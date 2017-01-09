package be.insaneprogramming.cleanarch.entity;

public class Tenant {
	private String id;
	private String name;

	Tenant(String id, String name) {
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
