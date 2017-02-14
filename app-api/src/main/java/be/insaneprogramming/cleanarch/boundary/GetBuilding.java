package be.insaneprogramming.cleanarch.boundary;

import java.util.function.Consumer;

import be.insaneprogramming.cleanarch.requestmodel.GetBuildingRequest;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;

@FunctionalInterface
@Boundary
public interface GetBuilding {
	void execute(GetBuildingRequest request, Consumer<BuildingResponseModel> responseModelFunction);
}
