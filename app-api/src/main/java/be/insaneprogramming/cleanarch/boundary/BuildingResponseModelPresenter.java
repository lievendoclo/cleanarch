package be.insaneprogramming.cleanarch.boundary;

import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;

@FunctionalInterface
@Boundary
public interface BuildingResponseModelPresenter<T> {
	T present(BuildingResponseModel buildingResponse);
}
