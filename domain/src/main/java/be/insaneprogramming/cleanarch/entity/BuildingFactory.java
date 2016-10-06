package be.insaneprogramming.cleanarch.entity;

import java.util.UUID;

import javax.inject.Named;

@Named
public class BuildingFactory {

	public Building createBuilding(String id, String name) {
		return new Building(id, name);
	}

	public Building createBuilding(String name) {
		return createBuilding(UUID.randomUUID().toString(), name);
	}
}
