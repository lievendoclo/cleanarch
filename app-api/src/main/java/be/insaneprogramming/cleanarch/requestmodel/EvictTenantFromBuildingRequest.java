package be.insaneprogramming.cleanarch.requestmodel;

import org.immutables.value.Value;

@Value.Immutable
public interface EvictTenantFromBuildingRequest {
	String getBuildingId();
	String getTenantId();
}