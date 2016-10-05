package be.insaneprogramming.cleanarch.interactor;

import javax.inject.Inject;
import javax.inject.Named;

import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest;

@Named
public class EvictTenantFromBuildingImpl implements EvictTenantFromBuilding {
	@Inject
	private BuildingEntityGateway buildingEntityGateway;

	@Override
	public void execute(EvictTenantFromBuildingRequest request) {
		Building building = buildingEntityGateway.findById(request.getBuildingId());
		building.evictTenant(request.getTenantId());
		buildingEntityGateway.save(building);
	}
}
