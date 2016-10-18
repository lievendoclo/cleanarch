package be.insaneprogramming.cleanarch.rest.viewmodel;

import org.immutables.value.Value;

@Value.Immutable
public interface TenantJson {
	String getId();
	String getName();
}
