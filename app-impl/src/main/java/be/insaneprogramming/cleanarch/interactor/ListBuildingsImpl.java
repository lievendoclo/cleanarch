package be.insaneprogramming.cleanarch.interactor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModelBuilder;

@Named
public class ListBuildingsImpl implements ListBuildings {
	private BuildingEntityGateway buildingEntityGateway;

	@Inject
	public ListBuildingsImpl(BuildingEntityGateway buildingEntityGateway) {
		this.buildingEntityGateway = buildingEntityGateway;
	}

	@Override
	public List<BuildingResponseModel> execute(ListBuildingsRequest request) {
		if(request == null) {
			throw new IllegalArgumentException("request should not be null");
		}
		List<BuildingResponseModel> response = new ArrayList<>();
		buildingEntityGateway.findAll().forEach(b ->
				response.add(BuildingResponseModelBuilder.builder().id(b.id).name(b.name).build())
		);
		return response;
	}
}
