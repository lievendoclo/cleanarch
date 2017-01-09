package be.insaneprogramming.cleanarch.interactor;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entity.TenantFactory;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;

public class AddTenantToBuildingImpl implements AddTenantToBuilding {

	private final BuildingEntityGateway buildingEntityGateway;

	public AddTenantToBuildingImpl(BuildingEntityGateway buildingEntityGateway) {
		this.buildingEntityGateway = buildingEntityGateway;
	}

	@Override
	public String execute(AddTenantToBuildingRequest request) {
		Building building = buildingEntityGateway.findById(request.getBuildingId());
		Tenant tenant = TenantFactory.create().createTenant(request.getTenantName());
		building.addTenant(tenant);
		buildingEntityGateway.save(building);
		return tenant.getId();
	}
}
