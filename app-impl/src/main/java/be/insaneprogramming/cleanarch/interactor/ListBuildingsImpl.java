package be.insaneprogramming.cleanarch.interactor;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.entity.Building;
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
		final List<Building> buildings;
		if(request.getNameStartsWith().isPresent()) {
			buildings = buildingEntityGateway.findByNameStartingWith(request.getNameStartsWith().get());
		} else {
			buildings = buildingEntityGateway.findAll();
		}
		buildings.stream().map(b -> {
						List<TenantResponseModel> tenantResponseModels = b.getTenants().stream().map(it -> new TenantResponseModel(it.getId(), it.getName())).collect(Collectors.toList());
							return new BuildingResponseModel(b.getId(), b.getName(), tenantResponseModels);
						}).forEach(buildingResponseModelConsumer);
	}
}
