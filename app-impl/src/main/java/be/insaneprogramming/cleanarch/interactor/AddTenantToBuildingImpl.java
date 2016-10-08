package be.insaneprogramming.cleanarch.interactor;

import javax.inject.Inject;
import javax.inject.Named;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.ImmutableBuildingId;
import be.insaneprogramming.cleanarch.entity.TenantFactory;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;

@Named
public class AddTenantToBuildingImpl implements AddTenantToBuilding {
	private BuildingEntityGateway buildingEntityGateway;
	private TenantFactory tenantFactory;

	@Inject
	public AddTenantToBuildingImpl(BuildingEntityGateway buildingEntityGateway, TenantFactory tenantFactory) {
		this.buildingEntityGateway = buildingEntityGateway;
		this.tenantFactory = tenantFactory;
	}

	@Override
	public void execute(AddTenantToBuildingRequest request) {
		if(request == null) {
			throw new IllegalArgumentException("request should not be null");
		}
		Building building = buildingEntityGateway.findById(ImmutableBuildingId.of(request.getBuildingId()));
		building.addTenant(tenantFactory.createTenant(request.getName()));
		buildingEntityGateway.save(building);
	}
}
