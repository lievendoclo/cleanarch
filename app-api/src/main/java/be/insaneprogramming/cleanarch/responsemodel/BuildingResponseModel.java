package be.insaneprogramming.cleanarch.responsemodel;

import java.util.List;

public final class BuildingResponseModel {
	private final String id;
	private final String name;
	private final List<TenantResponseModel> tenants;

	public BuildingResponseModel(String id, String name, List<TenantResponseModel> tenants) {
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

	public List<TenantResponseModel> getTenants() {
		return tenants;
	}
}
