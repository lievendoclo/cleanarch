package be.insaneprogramming.cleanarch.entitygatewayimpl.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

import be.insaneprogramming.cleanarch.entity.TenantId;

@Entity
public class TenantJpaEntity {
	@Id
	private String id;
	private String name;

	TenantJpaEntity() {
	}

	public TenantJpaEntity(TenantId id, String name) {
		this.id = id.getValue();
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
