package be.insaneprogramming.cleanarch.boundary;

import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;
import be.insaneprogramming.cleanarch.responsemodel.ListBuildingsResponse;

@FunctionalInterface
public interface ListBuildings {
	ListBuildingsResponse execute(ListBuildingsRequest request);
}
