package be.insaneprogramming.cleanarch.entity;

import java.util.ArrayList;
import java.util.List;

public class Building {
	public BuildingId id;
	public String name;
	public List<Tenant> tenants;

	public Building(BuildingId id, String name) {
		this.id = id;
		this.name = name;
		this.tenants = new ArrayList<>();
	}

	public BuildingId getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Tenant> getTenants() {
		return tenants;
	}

	public void addTenant(Tenant tenant) {
		this.tenants.add(tenant);
	}

	public void evictTenant(String tenantId) {
		this.tenants.removeIf(it -> it.getId().equals(tenantId));
	}
}
