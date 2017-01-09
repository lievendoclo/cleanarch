package be.insaneprogramming.cleanarch.event;

import org.skife.jdbi.v2.DBI;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingFactory;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jdbi.BuildingDao;

public class BuildingCreatedEventListener implements EventListener<BuildingCreated> {
	private DBI dbi;

	public BuildingCreatedEventListener(DBI dbi) {
		this.dbi = dbi;
	}

	@Override
	public Class<BuildingCreated> getEventClass() {
		return BuildingCreated.class;
	}

	@Override
	public void onEvent(BuildingCreated event) {
		try(BuildingDao buildingDao = dbi.open(BuildingDao.class)) {
			final Building building = BuildingFactory.create().createBuilding(event.getId(), event.getName());
			buildingDao.insert(building);
		}
	}
}
