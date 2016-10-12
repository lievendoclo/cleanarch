package be.insaneprogramming.cleanarch.interactor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;
import be.insaneprogramming.cleanarch.responsemodel.ImmutableBuildingResponseModel;
import be.insaneprogramming.cleanarch.responsemodel.ImmutableTenantResponseModel;

public class ListBuildingsImpl implements ListBuildings {
	private BuildingEntityGateway buildingEntityGateway;

	public ListBuildingsImpl(BuildingEntityGateway buildingEntityGateway) {
		this.buildingEntityGateway = buildingEntityGateway;
	}

	@Override
	public List<BuildingResponseModel> execute(ListBuildingsRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("request should not be null");
		}
		List<BuildingResponseModel> response = new ArrayList<>();
		buildingEntityGateway.findAll().forEach(b -> {
					BuildingResponseModel buildingResponseModel = ImmutableBuildingResponseModel.builder()
							.id(b.getId().get())
							.name(b.getName())
							.tenants(b.getTenants().stream().map(t ->
									ImmutableTenantResponseModel.builder().id(t.getId().get()).name(t.getName()).build()
							).collect(Collectors.toList()))
							.build();
					response.add(buildingResponseModel);
				}
		);
		return response;
	}
}
