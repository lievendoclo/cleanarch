package be.insaneprogramming.cleanarch.entity;

import java.util.ArrayList;
import java.util.List;

public class Building {
	public String id;
	public String name;
	public List<Tenant> tenants;

	Building(String id, String name) {
		this.id = id;
		this.name = name;
		this.tenants = new ArrayList<>();
	}

	public void addTenant(Tenant tenant) {
		this.tenants.add(tenant);
	}

	public void evictTenant(String tenantId) {
		this.tenants.removeIf(it -> it.getId().equals(tenantId));
	}
}
