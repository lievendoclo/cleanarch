package be.insaneprogramming.cleanarch.boundary;

import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;

@FunctionalInterface
@Boundary
public interface AddTenantToBuilding {
	String execute(AddTenantToBuildingRequest request);
}
