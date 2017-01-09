package be.insaneprogramming.cleanarch.entity;

import java.util.List;
import java.util.UUID;

public class BuildingFactory {
	private BuildingFactory() {}

	public Building createBuilding(String id, String name) {
		return new Building(id, name);
	}

	public Building createBuilding(String name) {
		return createBuilding(UUID.randomUUID().toString(), name);
	}

	public Building createBuilding(String id, String name, List<Tenant> tenants) {
		final Building building = createBuilding(id, name);
		tenants.forEach(building::addTenant);
		return building;
	}

	public static BuildingFactory create() {
		return new BuildingFactory();
	}
}
