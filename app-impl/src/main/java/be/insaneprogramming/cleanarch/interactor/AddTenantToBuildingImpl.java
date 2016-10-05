package be.insaneprogramming.cleanarch.interactor;

import javax.inject.Inject;
import javax.inject.Named;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.TenantFactory;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;

@Named
public class AddTenantToBuildingImpl implements AddTenantToBuilding {
	@Inject
	private BuildingEntityGateway buildingEntityGateway;
	@Inject
	private TenantFactory tenantFactory;

	@Override
	public void execute(AddTenantToBuildingRequest request) {
		Building building = buildingEntityGateway.findById(request.getBuildingId());
		building.addTenant(tenantFactory.createTenant(request.getName()));
		buildingEntityGateway.save(building);
	}
}
