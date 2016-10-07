package be.insaneprogramming.cleanarch.boundary;

import java.util.List;

import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;

@FunctionalInterface
public interface ListBuildings {
	List<BuildingResponseModel> execute(ListBuildingsRequest request);
}
