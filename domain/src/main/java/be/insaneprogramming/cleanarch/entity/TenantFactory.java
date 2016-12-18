package be.insaneprogramming.cleanarch.entity;

import java.util.UUID;

public class TenantFactory {
	public Tenant createTenant(String id, String name) {
		return new Tenant(id, name);
	}

	public Tenant createTenant(String name) {
		return createTenant(UUID.randomUUID().toString(), name);
	}
}
