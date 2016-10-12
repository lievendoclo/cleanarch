package be.insaneprogramming.cleanarch.requestmodel;

import org.immutables.value.Value;

@Value.Immutable
public interface EvictTenantFromBuildingRequest {
	@Value.Parameter
	String getBuildingId();
	@Value.Parameter
	String getTenantId();
}