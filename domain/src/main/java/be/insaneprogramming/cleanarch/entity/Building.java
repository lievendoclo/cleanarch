package be.insaneprogramming.cleanarch.entity;

import java.util.ArrayList;
import java.util.List;

public class Building {
	private String id;
	private String name;
	private List<Tenant> tenants;

	public Building(String id, String name) {
		this(id, name, new ArrayList<>());
	}

	public Building(String id, String name, List<Tenant> tenants) {
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

	public List<Tenant> getTenants() {
		return tenants;
	}

	public void addTenant(Tenant tenant) {
		this.tenants.add(tenant);
	}

	public void evictTenant(String tenantId) {
		this.tenants.removeIf(it -> it.getId().equals(tenantId) );
	}
}
