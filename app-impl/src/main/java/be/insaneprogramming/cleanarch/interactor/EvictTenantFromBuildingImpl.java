package be.insaneprogramming.cleanarch.interactor;

import javax.inject.Inject;
import javax.inject.Named;

import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest;

@Named
public class EvictTenantFromBuildingImpl implements EvictTenantFromBuilding {
	private BuildingEntityGateway buildingEntityGateway;

	@Inject
	public EvictTenantFromBuildingImpl(BuildingEntityGateway buildingEntityGateway) {
		this.buildingEntityGateway = buildingEntityGateway;
	}

	@Override
	public void execute(EvictTenantFromBuildingRequest request) {
		if(request == null) {
			throw new IllegalArgumentException("request should not be null");
		}
		Building building = buildingEntityGateway.findById(request.getBuildingId());
		building.evictTenant(request.getTenantId());
		buildingEntityGateway.save(building);
	}
}
