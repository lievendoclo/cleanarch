package be.insaneprogramming.cleanarch.boundary;

import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;
import be.insaneprogramming.cleanarch.responsemodel.CreateBuildingResponse;

@FunctionalInterface
public interface CreateBuilding {
	CreateBuildingResponse execute(CreateBuildingRequest request);
}
