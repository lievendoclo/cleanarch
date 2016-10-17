package be.insaneprogramming.cleanarch.entitygatewayimpl.morphia;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class BuildingDocument {
	@Id
	private String id;
	private String name;
	@Embedded
	private List<TenantDocument> tenants;

	private BuildingDocument() {
	}

	public BuildingDocument(String id, String name, List<TenantDocument> tenants) {
		this.id = id;
		this.name = name;
		this.tenants = tenants;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<TenantDocument> getTenants() {
		return tenants;
	}
}
