package be.insaneprogramming.cleanarch.entitygatewayimpl.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TenantJpaEntity {
	@Id
	private String id;
	private String name;

	protected TenantJpaEntity() {
	}

	public TenantJpaEntity(String id, String name) {
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
