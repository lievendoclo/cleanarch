package be.insaneprogramming.cleanarch.event;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingFactory;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntity;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntityRepository;

public class BuildingCreatedEventListener implements EventListener<BuildingCreated> {
	private BuildingJpaEntityRepository buildingJpaEntityRepository;

	public BuildingCreatedEventListener(BuildingJpaEntityRepository buildingJpaEntityRepository) {
		this.buildingJpaEntityRepository = buildingJpaEntityRepository;
	}

	@Override
	public Class<BuildingCreated> getEventClass() {
		return BuildingCreated.class;
	}

	@Override
	public void onEvent(BuildingCreated event) {
		final Building building = BuildingFactory.create().createBuilding(event.getId(), event.getName());
		buildingJpaEntityRepository.save(fromDomain(building));
	}

	private BuildingJpaEntity fromDomain(Building building) {
		return new BuildingJpaEntity(building.getId(), building.getName());
	}
}
