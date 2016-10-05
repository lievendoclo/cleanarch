package be.insaneprogramming.cleanarch.boundary;

import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;

@FunctionalInterface
public interface AddTenantToBuilding {
	void execute(AddTenantToBuildingRequest request);
}
