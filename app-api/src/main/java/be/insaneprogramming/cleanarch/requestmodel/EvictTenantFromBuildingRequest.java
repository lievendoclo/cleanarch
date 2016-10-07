package be.insaneprogramming.cleanarch.requestmodel;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(visibility = Value.Style.ImplementationVisibility.PRIVATE, newBuilder = "builder")
public interface EvictTenantFromBuildingRequest {
	String getBuildingId();
	String getTenantId();
}