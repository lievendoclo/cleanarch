package be.insaneprogramming.cleanarch.interactor;

import javax.inject.Inject;
import javax.inject.Named;

import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingRequest;
import be.insaneprogramming.cleanarch.responsemodel.ListBuildingResponse;

@Named
public class ListBuildingsImpl implements ListBuildings {
	private BuildingEntityGateway buildingEntityGateway;

	@Inject
	public ListBuildingsImpl(BuildingEntityGateway buildingEntityGateway) {
		this.buildingEntityGateway = buildingEntityGateway;
	}

	@Override
	public ListBuildingResponse execute(ListBuildingRequest request) {
		if(request == null) {
			throw new IllegalArgumentException("request should not be null");
		}
		ListBuildingResponse response = new ListBuildingResponse();
		buildingEntityGateway.findAll().forEach(b ->
				response.getItems().add(new ListBuildingResponse.ListBuildingResponseItem(b.id, b.name))
		);
		return response;
	}
}
