package be.insaneprogramming.cleanarch.entity;

import java.util.UUID;

import javax.inject.Named;

@Named
public class TenantFactory {
	public Tenant createTenant(String name) {
		return new Tenant(UUID.randomUUID().toString(), name);
	}
}
