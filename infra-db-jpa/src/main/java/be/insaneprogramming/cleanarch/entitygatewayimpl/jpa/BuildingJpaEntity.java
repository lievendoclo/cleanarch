package be.insaneprogramming.cleanarch.entitygatewayimpl.jpa;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class BuildingJpaEntity {
	@Id
	private String id;
	private String name;
	@OneToMany(cascade = { ALL }, fetch = EAGER)
	@JoinColumn(name = "building_id")
	private List<TenantJpaEntity> tenants;

	protected BuildingJpaEntity() {
	}

	public BuildingJpaEntity(String id, String name, List<TenantJpaEntity> tenants) {
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

	public List<TenantJpaEntity> getTenants() {
		return tenants;
	}
}