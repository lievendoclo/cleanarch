package be.insaneprogramming.cleanarch.interactor;

import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingId;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest;

public class EvictTenantFromBuildingImpl implements EvictTenantFromBuilding {
	private BuildingEntityGateway buildingEntityGateway;

	public EvictTenantFromBuildingImpl(BuildingEntityGateway buildingEntityGateway) {
		this.buildingEntityGateway = buildingEntityGateway;
	}

	@Override
	public void execute(EvictTenantFromBuildingRequest request) {
		if(request == null) {
			throw new IllegalArgumentException("request should not be null");
		}
		Building building = buildingEntityGateway.findById(BuildingId.of(request.getBuildingId()));
		building.evictTenant(request.getTenantId());
		buildingEntityGateway.save(building);
	}
}
