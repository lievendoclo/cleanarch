package be.insaneprogramming.cleanarch.interactor;

import javax.inject.Named;
import javax.transaction.Transactional;

import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest;

@Transactional
@Named
public class EvictTenantFromBuildingImpl implements EvictTenantFromBuilding {
	private final BuildingEntityGateway buildingEntityGateway;

	public EvictTenantFromBuildingImpl(BuildingEntityGateway buildingEntityGateway) {
		this.buildingEntityGateway = buildingEntityGateway;
	}

	@Override
	public void execute(EvictTenantFromBuildingRequest request) {
		Building building = buildingEntityGateway.findById(request.getBuildingId());
		building.evictTenant(request.getTenantId());
		buildingEntityGateway.save(building);
	}
}
