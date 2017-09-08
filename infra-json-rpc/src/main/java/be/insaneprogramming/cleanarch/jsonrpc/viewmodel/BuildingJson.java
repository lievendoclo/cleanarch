package be.insaneprogramming.cleanarch.jsonrpc.viewmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BuildingJson {
	private final String id;
	private final String name;
	private final List<TenantJson> tenants;

	@JsonCreator
	public BuildingJson(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("tenants") List<TenantJson> tenants) {
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

	public List<TenantJson> getTenants() {
		return tenants;
	}
}
