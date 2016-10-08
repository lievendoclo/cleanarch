package be.insaneprogramming.cleanarch.entity;

import org.immutables.value.Value;

@Value.Immutable
public interface TenantId {
	@Value.Parameter
	String get();
}
