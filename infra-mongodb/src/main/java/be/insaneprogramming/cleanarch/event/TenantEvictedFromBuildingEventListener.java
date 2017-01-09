package be.insaneprogramming.cleanarch.event;

import java.util.Objects;
import java.util.Optional;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import be.insaneprogramming.cleanarch.entitygatewayimpl.morphia.BuildingDocument;
import be.insaneprogramming.cleanarch.entitygatewayimpl.morphia.TenantDocument;

public class TenantEvictedFromBuildingEventListener implements EventListener<TenantEvictedFromBuilding> {
	private final Datastore datastore;

	public TenantEvictedFromBuildingEventListener(MongoClient mongo) {
		Morphia morphia = new Morphia();
		morphia.mapPackage("be.insaneprogramming.cleanarch.entitygatewayimpl.morphia");
		this.datastore = morphia.createDatastore(mongo, "cleanarch");
	}
	@Override
	public Class<TenantEvictedFromBuilding> getEventClass() {
		return TenantEvictedFromBuilding.class;
	}

	@Override
	public void onEvent(TenantEvictedFromBuilding event) {
		final BuildingDocument buildingDocument = datastore.get(BuildingDocument.class, event.getBuildingId());
		final Optional<TenantDocument> tenant = buildingDocument.getTenants().stream().filter(it -> Objects.equals(it.getId(), event.getTenantId())).findFirst();
		tenant.ifPresent(t -> buildingDocument.getTenants().remove(t));
		datastore.save(buildingDocument);
	}
}
