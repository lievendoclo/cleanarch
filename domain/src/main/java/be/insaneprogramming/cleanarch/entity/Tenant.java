package be.insaneprogramming.cleanarch.entity;

public class Tenant {
	private TenantId id;
	private String name;

	public Tenant(TenantId id, String name) {
		this.id = id;
		this.name = name;
	}

	public TenantId getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
