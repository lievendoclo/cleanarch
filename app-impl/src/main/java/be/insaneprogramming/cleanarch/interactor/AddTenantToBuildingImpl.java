package be.insaneprogramming.cleanarch.interactor;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entity.TenantFactory;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;

public class AddTenantToBuildingImpl implements AddTenantToBuilding {

	private final BuildingEntityGateway buildingEntityGateway;
	private final TenantFactory tenantFactory;

	public AddTenantToBuildingImpl(BuildingEntityGateway buildingEntityGateway, TenantFactory tenantFactory) {
		this.buildingEntityGateway = buildingEntityGateway;
		this.tenantFactory = tenantFactory;
	}

	@Override
	public String execute(AddTenantToBuildingRequest request) {
		if(request == null) {
			throw new IllegalArgumentException("request should not be null");
		}
		Building building = buildingEntityGateway.findById(request.getBuildingId());
		Tenant tenant = tenantFactory.createTenant(request.getTenantName());
		building.addTenant(tenant);
		buildingEntityGateway.save(building);
		return tenant.getId();
	}
}
