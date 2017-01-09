package be.insaneprogramming.cleanarch.interactor;

import be.insaneprogramming.cleanarch.boundary.CreateBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingFactory;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;

public class CreateBuildingImpl implements CreateBuilding {

	private final BuildingEntityGateway buildingEntityGateway;

	public CreateBuildingImpl(BuildingEntityGateway buildingEntityGateway) {
		this.buildingEntityGateway = buildingEntityGateway;
	}

	@Override
	public String execute(CreateBuildingRequest request) {
		Building building = BuildingFactory.create().createBuilding(request.getName());
		return buildingEntityGateway.save(building);
	}
}
