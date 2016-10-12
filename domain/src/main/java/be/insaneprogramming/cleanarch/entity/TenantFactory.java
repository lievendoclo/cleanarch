package be.insaneprogramming.cleanarch.entity;

import java.util.UUID;

public class TenantFactory {
	public Tenant createTenant(TenantId id, String name) {
		return new Tenant(id, name);
	}

	public Tenant createTenant(String name) {
		return createTenant(ImmutableTenantId.of(UUID.randomUUID().toString()), name);
	}
}
