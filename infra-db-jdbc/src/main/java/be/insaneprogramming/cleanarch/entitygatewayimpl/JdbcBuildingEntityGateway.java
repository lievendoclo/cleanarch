package be.insaneprogramming.cleanarch.entitygatewayimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingId;
import be.insaneprogramming.cleanarch.entity.ImmutableBuildingId;
import be.insaneprogramming.cleanarch.entity.ImmutableTenantId;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;

public class JdbcBuildingEntityGateway implements BuildingEntityGateway {
	private NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcBuildingEntityGateway(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public String save(Building building) {
		try {
			findById(building.getId());
			return update(building);
		} catch(DataAccessException dae) {
			return insert(building);
		}
	}

	private String insert(Building building) {
		String query = "INSERT INTO building(id, name) VALUES (:id, :name)";
		Map<String, String> params = new HashMap<>();
		params.put("id", building.getId().get());
		params.put("name", building.getName());
		jdbcTemplate.update(query, params);
		saveTenants(building);
		return building.getId().get();
	}

	private String update(Building building) {
		String query = "UPDATE building SET name = :name WHERE id = :id";
		Map<String, String> params = new HashMap<>();
		params.put("id", building.getId().get());
		params.put("name", building.getName());
		jdbcTemplate.update(query, params);
		saveTenants(building);
		return building.getId().get();
	}

	private void saveTenants(Building building) {
		deleteAllTenants(building.getId());
		building.getTenants().forEach(it -> insert(building.getId(), it));
	}

	private void deleteAllTenants(BuildingId buildingId) {
		String query = "DELETE FROM tenant WHERE buildingId = :buildingId";
		Map<String, String> params = new HashMap<>();
		params.put("buildingId", buildingId.get());
		jdbcTemplate.update(query, params);
	}

	private String insert(BuildingId buildingId, Tenant tenant) {
		String query = "INSERT INTO tenant (id, name, buildingId) VALUES (:id, :name, :buildingId)";
		Map<String, String> params = new HashMap<>();
		params.put("id", tenant.getId().get());
		params.put("name", tenant.getName());
		params.put("buildingId", buildingId.get());
		jdbcTemplate.update(query, params);
		return tenant.getId().get();
	}

	@Override
	public List<Building> findAll() {
		String query = "SELECT * FROM building";
		Map<String, String> params = new HashMap<>();
		return jdbcTemplate.query(query, params, (rs, rowNum) -> {
			ImmutableBuildingId id = ImmutableBuildingId.of(rs.getString("id"));
			return new Building(id, rs.getString("name"), findByBuildingId(id));
		});
	}

	@Override
	public Building findById(BuildingId id) {
		String query = "SELECT * FROM building WHERE id = :id";
		Map<String, String> params = new HashMap<>();
		params.put("id", id.get());
		return jdbcTemplate.queryForObject(query, params, (rs, rowNum) -> new Building(ImmutableBuildingId.of(rs.getString("id")), rs.getString("name")));
	}

	private List<Tenant> findByBuildingId(BuildingId buildingId) {
		String query = "SELECT * FROM tenant WHERE buildingId = :buildingId";
		Map<String, String> params = new HashMap<>();
		params.put("buildingId", buildingId.get());
		return jdbcTemplate.query(query, params, (rs, rowNum) -> new Tenant(ImmutableTenantId.of(rs.getString("id")), rs.getString("name")));
	}
}
