package be.insaneprogramming.cleanarch.entitygatewayimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mongodb.morphia.Datastore;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingId;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entity.TenantId;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.morphia.BuildingDocument;
import be.insaneprogramming.cleanarch.entitygatewayimpl.morphia.TenantDocument;

public class MongoDbBuildingEntityGateway implements BuildingEntityGateway {

	private Datastore datastore;

	public MongoDbBuildingEntityGateway(Datastore datastore) {
		this.datastore = datastore;
	}

	@Override
	public String save(Building building) {
		return (String) datastore.save(toDocument(building)).getId();
	}

	private BuildingDocument toDocument(Building building) {
		return new BuildingDocument(building.getId().getValue(), building.getName(), building.getTenants().stream().map(this::toDocument).collect(Collectors.toList()));
	}

	private TenantDocument toDocument(Tenant tenant) {
		return new TenantDocument(tenant.getId().getValue(), tenant.getName());
	}

	@Override
	public List<Building> findAll() {
		return datastore.find(BuildingDocument.class).asList().stream().map(this::fromDocument).collect(Collectors.toList());
	}

	@Override
	public Building findById(BuildingId id) {
		return fromDocument(datastore.get(BuildingDocument.class, id.getValue()));
	}

	private Building fromDocument(BuildingDocument document) {
		return new Building(BuildingId.of(document.getId()), document.getName(), document.getTenants() != null ? document.getTenants().stream().map(this::fromDocument).collect(Collectors.toList()) : new ArrayList<>());
	}

	private Tenant fromDocument(TenantDocument document) {
		return new Tenant(TenantId.of(document.getId()), document.getName());

	}
}
