package be.insaneprogramming.cleanarch.rest.viewmodel;

public class TenantJson {
	private final String id;
	private final String name;

	public TenantJson(String id, String name) {
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
