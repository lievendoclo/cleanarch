package be.insaneprogramming.cleanarch.boundary;

import java.util.List;

import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;

public interface BuildingListPresenter<T> {
	T present(List<BuildingResponseModel> buildingResponses);
}
