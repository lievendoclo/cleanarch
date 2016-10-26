package be.insaneprogramming.cleanarch.rest.viewmodel;

import java.util.List;

public class BuildingJson {
	private final String id;
	private final String name;
	private final List<TenantJson> tenants;

	public BuildingJson(String id, String name, List<TenantJson> tenants) {
		this.id = id;
		this.name = name;
		this.tenants = tenants;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<TenantJson> getTenants() {
		return tenants;
	}
}
