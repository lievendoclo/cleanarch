package be.insaneprogramming.cleanarch.event;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entity.TenantFactory;
import be.insaneprogramming.cleanarch.entitygatewayimpl.morphia.BuildingDocument;
import be.insaneprogramming.cleanarch.entitygatewayimpl.morphia.TenantDocument;

public class TenantAddedToBuildingEventListener implements EventListener<TenantAddedToBuilding> {
	private final Datastore datastore;

	public TenantAddedToBuildingEventListener(MongoClient mongo) {
		Morphia morphia = new Morphia();
		morphia.mapPackage("be.insaneprogramming.cleanarch.entitygatewayimpl.morphia");
		this.datastore = morphia.createDatastore(mongo, "cleanarch");
	}
	@Override
	public Class<TenantAddedToBuilding> getEventClass() {
		return TenantAddedToBuilding.class;
	}

	@Override
	public void onEvent(TenantAddedToBuilding event) {
		final Tenant tenant = TenantFactory.create().createTenant(event.getTenantId(), event.getName());
		final BuildingDocument buildingDocument = datastore.get(BuildingDocument.class, event.getBuildingId());
		buildingDocument.getTenants().add(toDocument(tenant));
		datastore.save(buildingDocument);
	}

	private TenantDocument toDocument(Tenant tenant)  {
		return new TenantDocument(tenant.getId(), tenant.getName());
	}

}
