package be.insaneprogramming.cleanarch.responsemodel;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

@Immutable
public class BuildingResponseModel {
	private final String id;
	private final String name;
	private final Iterable<TenantResponseModel> tenants;

	public BuildingResponseModel(String id, String name, Iterable<TenantResponseModel> tenants) {
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

	@Nonnull
	public Iterable<TenantResponseModel> getTenants() {
		return tenants;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BuildingResponseModel that = (BuildingResponseModel) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(name, that.name) &&
				Objects.equals(tenants, that.tenants);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, tenants);
	}
}
