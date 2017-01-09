package be.insaneprogramming.cleanarch.event;

import java.util.Objects;
import java.util.Optional;

import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntity;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntityRepository;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.TenantJpaEntity;

public class TenantEvictedFromBuildingEventListener implements EventListener<TenantEvictedFromBuilding> {
	private BuildingJpaEntityRepository buildingJpaEntityRepository;

	public TenantEvictedFromBuildingEventListener(BuildingJpaEntityRepository buildingJpaEntityRepository) {
		this.buildingJpaEntityRepository = buildingJpaEntityRepository;
	}

	@Override
	public Class<TenantEvictedFromBuilding> getEventClass() {
		return TenantEvictedFromBuilding.class;
	}

	@Override
	public void onEvent(TenantEvictedFromBuilding event) {
		final BuildingJpaEntity building = buildingJpaEntityRepository.findOne(event.getBuildingId());
		final Optional<TenantJpaEntity> tenant = building.getTenants().stream().filter(it -> Objects.equals(it.getId(), event.getTenantId())).findFirst();
		tenant.ifPresent(t -> building.getTenants().remove(t));
		buildingJpaEntityRepository.save(building);
	}
}
