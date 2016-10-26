package be.insaneprogramming.cleanarch.entitygatewayimpl.jpa;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import be.insaneprogramming.cleanarch.entity.BuildingId;

@Entity
public class BuildingJpaEntity {
	@Id
	private String id;
	private String name;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "building_id")
	private List<TenantJpaEntity> tenants;

	BuildingJpaEntity() {
	}

	public BuildingJpaEntity(BuildingId id, String name, List<TenantJpaEntity> tenants) {
		this.id = id.getValue();
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
