package be.insaneprogramming.cleanarch.interactor;

import javax.inject.Inject;
import javax.inject.Named;

import be.insaneprogramming.cleanarch.boundary.CreateBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingFactory;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;
import be.insaneprogramming.cleanarch.responsemodel.CreateBuildingResponse;

@Named
public class CreateBuildingImpl implements CreateBuilding {
	@Inject
	private BuildingEntityGateway buildingEntityGateway;
	@Inject
	private BuildingFactory buildingFactory;

	@Override
	public CreateBuildingResponse execute(CreateBuildingRequest request) {
		Building building = buildingFactory.createBuilding(request.getName());
		return new CreateBuildingResponse(buildingEntityGateway.save(building));
	}
}
