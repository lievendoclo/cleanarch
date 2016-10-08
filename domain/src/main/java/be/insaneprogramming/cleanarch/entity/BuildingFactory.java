package be.insaneprogramming.cleanarch.entity;

import java.util.UUID;

import javax.inject.Named;

@Named
public class BuildingFactory {

	public Building createBuilding(BuildingId id, String name) {
		return new Building(id, name);
	}

	public Building createBuilding(String name) {
		return createBuilding(ImmutableBuildingId.of(UUID.randomUUID().toString()), name);
	}
}
