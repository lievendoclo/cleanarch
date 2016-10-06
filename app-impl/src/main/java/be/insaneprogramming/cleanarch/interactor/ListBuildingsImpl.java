package be.insaneprogramming.cleanarch.interactor;

import javax.inject.Inject;
import javax.inject.Named;

import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;
import be.insaneprogramming.cleanarch.responsemodel.ListBuildingsResponse;

@Named
public class ListBuildingsImpl implements ListBuildings {
	private BuildingEntityGateway buildingEntityGateway;

	@Inject
	public ListBuildingsImpl(BuildingEntityGateway buildingEntityGateway) {
		this.buildingEntityGateway = buildingEntityGateway;
	}

	@Override
	public ListBuildingsResponse execute(ListBuildingsRequest request) {
		if(request == null) {
			throw new IllegalArgumentException("request should not be null");
		}
		ListBuildingsResponse response = new ListBuildingsResponse();
		buildingEntityGateway.findAll().forEach(b ->
				response.getItems().add(new ListBuildingsResponse.ListBuildingsResponseItem(b.id, b.name))
		);
		return response;
	}
}
