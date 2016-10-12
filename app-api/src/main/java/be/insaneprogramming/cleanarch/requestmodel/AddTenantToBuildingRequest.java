package be.insaneprogramming.cleanarch.requestmodel;

import org.immutables.value.Value;

@Value.Immutable
public interface AddTenantToBuildingRequest {
	@Value.Parameter
	String getBuildingId();
	@Value.Parameter
	String getName();
}
