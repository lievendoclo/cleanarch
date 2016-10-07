package be.insaneprogramming.cleanarch.entitygatewayimpl.jpa;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class BuildingJpaEntity {
	@Id
	private String id;
	private String name;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "building_id")
	private List<TenantJpaEntity> tenants;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TenantJpaEntity> getTenants() {
		return tenants;
	}

	public void setTenants(List<TenantJpaEntity> tenants) {
		this.tenants = tenants;
	}
}
