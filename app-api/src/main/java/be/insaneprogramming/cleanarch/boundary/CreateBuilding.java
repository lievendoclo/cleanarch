package be.insaneprogramming.cleanarch.boundary;

import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;

@FunctionalInterface
public interface CreateBuilding {
	String execute(CreateBuildingRequest request);
}
