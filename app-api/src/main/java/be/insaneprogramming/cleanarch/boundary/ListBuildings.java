package be.insaneprogramming.cleanarch.boundary;

import be.insaneprogramming.cleanarch.requestmodel.ListBuildingRequest;
import be.insaneprogramming.cleanarch.responsemodel.ListBuildingResponse;

@FunctionalInterface
public interface ListBuildings {
	ListBuildingResponse execute(ListBuildingRequest request);
}
