package be.insaneprogramming.cleanarch.event;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingFactory;
import be.insaneprogramming.cleanarch.entitygatewayimpl.morphia.BuildingDocument;

public class BuildingCreatedEventListener implements EventListener<BuildingCreated> {
	private final Datastore datastore;

	public BuildingCreatedEventListener(MongoClient mongo) {
		Morphia morphia = new Morphia();
		morphia.mapPackage("be.insaneprogramming.cleanarch.entitygatewayimpl.morphia");
		this.datastore = morphia.createDatastore(mongo, "cleanarch");
	}

	@Override
	public Class<BuildingCreated> getEventClass() {
		return BuildingCreated.class;
	}

	@Override
	public void onEvent(BuildingCreated event) {
		final Building building = BuildingFactory.create().createBuilding(event.getId(), event.getName());
		datastore.save(toDocument(building));
	}

	private BuildingDocument toDocument(Building building)  {
		return new BuildingDocument(building.getId(), building.getName());
	}
}
