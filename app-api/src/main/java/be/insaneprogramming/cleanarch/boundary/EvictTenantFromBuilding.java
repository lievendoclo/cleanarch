package be.insaneprogramming.cleanarch.boundary;

import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest;

@FunctionalInterface
@Boundary
public interface EvictTenantFromBuilding {
	void execute(EvictTenantFromBuildingRequest request);
}
