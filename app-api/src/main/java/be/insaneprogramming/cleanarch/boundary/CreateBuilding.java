package be.insaneprogramming.cleanarch.boundary;

import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;

@FunctionalInterface
@Boundary
public interface CreateBuilding {
	String execute(CreateBuildingRequest request);
}
