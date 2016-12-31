package be.insaneprogramming.cleanarch.entitygatewayimpl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.morphia.BuildingDocument;
import be.insaneprogramming.cleanarch.entitygatewayimpl.morphia.TenantDocument;

public class MongoDbBuildingEntityGateway implements BuildingEntityGateway {
	private final Datastore datastore;

	public MongoDbBuildingEntityGateway(Datastore datastore) {
		this.datastore = datastore;
	}

	public String save(Building building) {
		datastore.save(toDocument(building));
		return building.getId();
	}

	public List<Building> findAll() {
		return datastore.find(BuildingDocument.class).asList().stream().map(this::toDomain).collect(toList());
	}

	@Override
	public List<Building> findByNameStartingWith(String name) {
		final Query<BuildingDocument> query = datastore.createQuery(BuildingDocument.class);
		query.criteria("name").startsWith("name");
		return query.asList().stream().map(this::toDomain).collect(toList());
	}

	public Building findById(String id)  {
		return toDomain(datastore.get(BuildingDocument.class, id));
	}

	private Building toDomain(BuildingDocument building)  {
		return new Building(building.getId(), building.getName(), building.getTenants().stream().map(this::toDomain).collect(toList()));
	}

	private Tenant toDomain(TenantDocument tenant) {
		return new Tenant(tenant.getId(), tenant.getName());
	}

	private BuildingDocument toDocument(Building building)  {
		return new BuildingDocument(building.getId(), building.getName(), building.getTenants().stream().map(this::toDocument).collect(toList()));
	}

	private TenantDocument toDocument(Tenant tenant)  {
		return new TenantDocument(tenant.getId(), tenant.getName());
	}
}
