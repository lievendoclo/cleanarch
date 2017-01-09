package be.insaneprogramming.cleanarch.interactor;

import java.util.List;
import java.util.stream.Collectors;

import be.insaneprogramming.cleanarch.boundary.BuildingResponseModelPresenter;
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
	public <T> List<T> execute(ListBuildingsRequest request, BuildingResponseModelPresenter<T> buildingResponseModelPresenter) {
		final List<Building> buildings;
		if(request.getNameStartsWith().isPresent()) {
			buildings = buildingEntityGateway.findByNameStartingWith(request.getNameStartsWith().get());
		} else {
			buildings = buildingEntityGateway.findAll();
		}
		return buildings.stream().map(b -> {
						List<TenantResponseModel> tenantResponseModels = b.getTenants().stream().map(it -> new TenantResponseModel(it.getId(), it.getName())).collect(Collectors.toList());
							return new BuildingResponseModel(b.getId(), b.getName(), tenantResponseModels);
						}).map(buildingResponseModelPresenter::present).collect(Collectors.toList());
	}
}
