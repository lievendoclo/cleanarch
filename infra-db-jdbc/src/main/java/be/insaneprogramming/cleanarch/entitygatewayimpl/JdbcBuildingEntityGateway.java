package be.insaneprogramming.cleanarch.entitygatewayimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.annotation.Transactional;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;

@Transactional
public class JdbcBuildingEntityGateway implements BuildingEntityGateway {
	private final NamedParameterJdbcOperations jdbcTemplate;

	public JdbcBuildingEntityGateway(NamedParameterJdbcOperations jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public String save(Building building) {
		try {
			findById(building.getId());
			return update(building);
		} catch (DataAccessException dae) {
			return insert(building);
		}
	}

	private String insert(Building building) {
		String query = "INSERT INTO building(id, name) VALUES (:id, :name)";
		Map<String, String> params = new HashMap<>();
		params.put("id", building.getId());
		params.put("name", building.getName());
		jdbcTemplate.update(query, params);
		saveTenants(building);
		return building.getId();
	}

	private String update(Building building) {
		String query = "UPDATE building SET name = :name WHERE id = :id";
		Map<String, String> params = new HashMap<>();
		params.put("id", building.getId());
		params.put("name", building.getName());
		jdbcTemplate.update(query, params);
		saveTenants(building);
		return building.getId();
	}

	private void saveTenants(Building building) {
		deleteAllTenants(building.getId());
		building.getTenants().forEach(it -> insert(building.getId(), it));
	}

	private void deleteAllTenants(String buildingId) {
		String query = "DELETE FROM tenant WHERE buildingId = :buildingId";
		Map<String, String> params = new HashMap<>();
		params.put("buildingId", buildingId);
		jdbcTemplate.update(query, params);
	}

	private String insert(String buildingId, Tenant tenant) {
		String query = "INSERT INTO tenant (id, name, buildingId) VALUES (:id, :name, :buildingId)";
		Map<String, String> params = new HashMap<>();
		params.put("id", tenant.getId());
		params.put("name", tenant.getName());
		params.put("buildingId", buildingId);
		jdbcTemplate.update(query, params);
		return tenant.getId();
	}

	public List<Building> findAll() {
		String query = "SELECT * FROM building";
		Map<String, String> params = new HashMap<>();
		return jdbcTemplate.query(query, params, (rs, rowNum) -> {
			String id = rs.getString("id");
			return new Building(id, rs.getString("name"), findTenantsByBuildingId(id));
		});
	}

	public Building findById(String buildingId) {
		String query = "SELECT * FROM building WHERE id = :id";
		Map<String, String> params = new HashMap<>();
		params.put("id", buildingId);
		return jdbcTemplate.queryForObject(query, params, (rs, rowNum) -> {
			String buildingName = rs.getString("name");
			return new Building(buildingId, buildingName, findTenantsByBuildingId(buildingId));
		});
	}

	private List<Tenant> findTenantsByBuildingId(String buildingId) {
		String query = "SELECT * FROM tenant WHERE buildingId = :buildingId";
		Map<String, String> params = new HashMap<>();
		params.put("buildingId", buildingId);
		return jdbcTemplate.query(query, params, (rs, rowNum) -> new Tenant(rs.getString("id"), rs.getString("name")));
	}
}
