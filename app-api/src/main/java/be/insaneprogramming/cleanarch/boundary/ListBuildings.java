package be.insaneprogramming.cleanarch.boundary;

import java.util.concurrent.CompletableFuture;

import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;

@FunctionalInterface
public interface ListBuildings {
	<T> CompletableFuture<T> execute(ListBuildingsRequest request, BuildingListPresenter<T> buildingListPresenter);
}
