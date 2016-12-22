package be.insaneprogramming.cleanarch.boundary;

import be.insaneprogramming.cleanarch.requestmodel.GetBuildingRequest;

@FunctionalInterface
@Boundary
public interface GetBuilding {
	<T> T execute(GetBuildingRequest request, BuildingResponseModelPresenter<T> presenter);
}
