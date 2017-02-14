package be.insaneprogramming.cleanarch.interactor;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;
import be.insaneprogramming.cleanarch.responsemodel.TenantResponseModel;

public class ListBuildingsImpl implements ListBuildings {
	private final BuildingEntityGateway buildingEntityGateway;

	public ListBuildingsImpl(BuildingEntityGateway buildingEntityGateway) {
		this.buildingEntityGateway = buildingEntityGateway;
	}

	@Override
	public void execute(ListBuildingsRequest request, Consumer<BuildingResponseModel> buildingResponseModelConsumer) {
		final List<Building> buildings = findBuildings(request);
		buildings.stream()
				.map(this::getBuildingResponseModel)
				.forEach(buildingResponseModelConsumer);
	}

	private BuildingResponseModel getBuildingResponseModel(Building b) {
		List<TenantResponseModel> tenantResponseModels = b.getTenants().stream()
				.map(this::getTenantResponseModel)
				.collect(Collectors.toList());
		return new BuildingResponseModel(b.getId(), b.getName(), tenantResponseModels);
	}

	private TenantResponseModel getTenantResponseModel(Tenant t) {
		return new TenantResponseModel(t.getId(), t.getName());
	}

	private List<Building> findBuildings(ListBuildingsRequest request) {
		return request.getNameStartsWith()
				.map(buildingEntityGateway::findByNameStartingWith)
				.orElse(buildingEntityGateway.findAll());
	}
}
