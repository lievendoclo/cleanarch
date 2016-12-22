package be.insaneprogramming.cleanarch.boundary;

import java.util.List;

import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;

@FunctionalInterface
@Boundary
public interface ListBuildings {
	<T> List<T> execute(ListBuildingsRequest request, BuildingResponseModelPresenter<T> buildingResponseModelPresenter);
}
