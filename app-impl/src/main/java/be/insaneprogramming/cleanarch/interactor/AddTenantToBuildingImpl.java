package be.insaneprogramming.cleanarch.interactor;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingId;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entity.TenantFactory;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;

public class AddTenantToBuildingImpl implements AddTenantToBuilding {
	private BuildingEntityGateway buildingEntityGateway;
	private TenantFactory tenantFactory;

	public AddTenantToBuildingImpl(BuildingEntityGateway buildingEntityGateway, TenantFactory tenantFactory) {
		this.buildingEntityGateway = buildingEntityGateway;
		this.tenantFactory = tenantFactory;
	}

	@Override
	public String execute(AddTenantToBuildingRequest request) {
		if(request == null) {
			throw new IllegalArgumentException("request should not be null");
		}
		Building building = buildingEntityGateway.findById(BuildingId.of(request.getBuildingId()));
		Tenant tenant = tenantFactory.createTenant(request.getName());
		building.addTenant(tenant);
		buildingEntityGateway.save(building);
		return tenant.getId().getValue();
	}
}
