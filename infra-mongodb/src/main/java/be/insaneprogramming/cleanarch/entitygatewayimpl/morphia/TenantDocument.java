package be.insaneprogramming.cleanarch.entitygatewayimpl.morphia;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class TenantDocument {
	@Id
	private String id;
	private String name;

	private TenantDocument() {}

	public TenantDocument(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
