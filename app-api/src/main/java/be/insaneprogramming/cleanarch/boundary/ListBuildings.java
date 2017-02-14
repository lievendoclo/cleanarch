package be.insaneprogramming.cleanarch.boundary;

import java.util.function.Consumer;

import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;

@FunctionalInterface
@Boundary
public interface ListBuildings {
	void execute(ListBuildingsRequest request, Consumer<BuildingResponseModel> responseModelFunction);
}
