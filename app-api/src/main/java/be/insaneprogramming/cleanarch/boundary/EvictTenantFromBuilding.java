package be.insaneprogramming.cleanarch.boundary;

import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest;

@FunctionalInterface
public interface EvictTenantFromBuilding {
	void execute(EvictTenantFromBuildingRequest request);
}
