package be.insaneprogramming.cleanarch.event;

import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entity.TenantFactory;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntity;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntityRepository;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.TenantJpaEntity;

public class TenantAddedToBuildingEventListener implements EventListener<TenantAddedToBuilding> {
	private BuildingJpaEntityRepository buildingJpaEntityRepository;

	public TenantAddedToBuildingEventListener(BuildingJpaEntityRepository buildingJpaEntityRepository) {
		this.buildingJpaEntityRepository = buildingJpaEntityRepository;
	}

	@Override
	public Class<TenantAddedToBuilding> getEventClass() {
		return TenantAddedToBuilding.class;
	}

	@Override
	public void onEvent(TenantAddedToBuilding event) {
		final Tenant tenant = TenantFactory.create().createTenant(event.getTenantId(), event.getName());
		final BuildingJpaEntity building = buildingJpaEntityRepository.findOne(event.getBuildingId());
		building.getTenants().add(fromDomain(tenant));
		buildingJpaEntityRepository.save(building);
	}

	private TenantJpaEntity fromDomain(Tenant tenant) {
		return new TenantJpaEntity(
				tenant.getId(),
				tenant.getName()
		);
	}
}
