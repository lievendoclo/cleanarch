package be.insaneprogramming.cleanarch.interactor;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import be.insaneprogramming.cleanarch.boundary.GetBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.GetBuildingRequest;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;
import be.insaneprogramming.cleanarch.responsemodel.TenantResponseModel;

public class GetBuildingImpl implements GetBuilding {
	private BuildingEntityGateway buildingEntityGateway;

	public GetBuildingImpl(BuildingEntityGateway buildingEntityGateway) {
		this.buildingEntityGateway = buildingEntityGateway;
	}

	@Override
	public void execute(GetBuildingRequest request, Consumer<BuildingResponseModel> consumer) {
		Building b = buildingEntityGateway.findById(request.getBuildingId());
		List<TenantResponseModel> tenants = b.getTenants().stream().map(it -> new TenantResponseModel(it.getId(), it.getName())).collect(Collectors.toList());
		consumer.accept(new BuildingResponseModel(b.getId(), b.getName(), tenants));
	}
}
