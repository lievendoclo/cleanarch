package be.insaneprogramming.cleanarch.responsemodel;

import org.immutables.value.Value;

@Value.Immutable
public interface TenantResponseModel {
	String getId();
	String getName();
}
