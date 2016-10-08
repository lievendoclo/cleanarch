package be.insaneprogramming.cleanarch.requestmodel;

import org.immutables.value.Value;

@Value.Immutable
public interface AddTenantToBuildingRequest {
	String getName();
	String getBuildingId();
}
