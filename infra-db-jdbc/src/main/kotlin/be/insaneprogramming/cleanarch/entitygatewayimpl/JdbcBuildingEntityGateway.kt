package be.insaneprogramming.cleanarch.entitygatewayimpl

import be.insaneprogramming.cleanarch.entity.Building
import be.insaneprogramming.cleanarch.entity.BuildingId
import be.insaneprogramming.cleanarch.entity.Tenant
import be.insaneprogramming.cleanarch.entity.TenantId
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.util.*

class JdbcBuildingEntityGateway(private val jdbcTemplate: NamedParameterJdbcTemplate) : BuildingEntityGateway {

    override fun save(building: Building): String {
        try {
            findById(building.id)
            return update(building)
        } catch (dae: DataAccessException) {
            return insert(building)
        }

    }

    private fun insert(building: Building): String {
        val query = "INSERT INTO building(id, name) VALUES (:id, :name)"
        val params = HashMap<String, String>()
        params.put("id", building.id.value)
        params.put("name", building.name)
        jdbcTemplate.update(query, params)
        saveTenants(building)
        return building.id.value
    }

    private fun update(building: Building): String {
        val query = "UPDATE building SET name = :name WHERE id = :id"
        val params = HashMap<String, String>()
        params.put("id", building.id.value)
        params.put("name", building.name)
        jdbcTemplate.update(query, params)
        saveTenants(building)
        return building.id.value
    }

    private fun saveTenants(building: Building) {
        deleteAllTenants(building.id)
        building.tenants.forEach { it -> insert(building.id, it) }
    }

    private fun deleteAllTenants(buildingId: BuildingId) {
        val query = "DELETE FROM tenant WHERE buildingId = :buildingId"
        val params = HashMap<String, String>()
        params.put("buildingId", buildingId.value)
        jdbcTemplate.update(query, params)
    }

    private fun insert(buildingId: BuildingId, tenant: Tenant): String {
        val query = "INSERT INTO tenant (id, name, buildingId) VALUES (:id, :name, :buildingId)"
        val params = HashMap<String, String>()
        params.put("id", tenant.id.value)
        params.put("name", tenant.name)
        params.put("buildingId", buildingId.value)
        jdbcTemplate.update(query, params)
        return tenant.id.value
    }

    override fun findAll(): List<Building> {
        val query = "SELECT * FROM building"
        val params = HashMap<String, String>()
        return jdbcTemplate.query(query, params) { rs, rowNum ->
            val id = rs.getString("id")
            Building(BuildingId(id), rs.getString("name"), findByBuildingId(BuildingId(id)))
        }
    }

    override fun findById(id: BuildingId): Building {
        val query = "SELECT * FROM building WHERE id = :id"
        val params = HashMap<String, String>()
        params.put("id", id.value)
        return jdbcTemplate.queryForObject(query, params) { rs, rowNum -> Building(BuildingId(rs.getString("id")), rs.getString("name")) }
    }

    private fun findByBuildingId(buildingId: BuildingId): MutableList<Tenant> {
        val query = "SELECT * FROM tenant WHERE buildingId = :buildingId"
        val params = HashMap<String, String>()
        params.put("buildingId", buildingId.value)
        return jdbcTemplate.query(query, params) { rs, rowNum -> Tenant(TenantId(rs.getString("id")), rs.getString("name")) }
    }
}
