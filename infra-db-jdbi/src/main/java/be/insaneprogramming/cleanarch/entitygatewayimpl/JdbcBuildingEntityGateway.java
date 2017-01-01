package be.insaneprogramming.cleanarch.entitygatewayimpl;

import java.util.List;

import org.skife.jdbi.v2.DBI;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jdbi.BuildingDao;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jdbi.TenantDao;

public class JdbcBuildingEntityGateway implements BuildingEntityGateway {
	private final DBI dbi;

	public JdbcBuildingEntityGateway(DBI dbi) {
		this.dbi = dbi;
	}

	@Override
	public String save(Building building) {
		final Building byId = findById(building.getId());
		if(byId == null) {
			return insert(building);
		} else {
			return update(building);
		}
	}

	private String insert(Building building) {
		BuildingDao buildingDao = dbi.open(BuildingDao.class);
		buildingDao.insert(building);
		buildingDao.close();
		saveTenants(building);
		return building.getId();
	}

	private String update(Building building) {
		BuildingDao buildingDao = dbi.open(BuildingDao.class);
		buildingDao.update(building);
		buildingDao.close();
		saveTenants(building);
		return building.getId();
	}

	private void saveTenants(Building building) {
		TenantDao tenantDao = dbi.open(TenantDao.class);
		tenantDao.deleteAllForBuilding(building.getId());
		tenantDao.close();
		building.getTenants().forEach(it -> insert(building.getId(), it));
	}

	private String insert(String buildingId, Tenant tenant) {
		TenantDao tenantDao = dbi.open(TenantDao.class);
		tenantDao.insert(buildingId, tenant);
		tenantDao.close();
		return tenant.getId();
	}

	public List<Building> findAll() {
		BuildingDao buildingDao = dbi.open(BuildingDao.class);
		final List<Building> buildings = buildingDao.findAll();
		buildingDao.close();
		return buildings;
	}

	@Override
	public List<Building> findByNameStartingWith(String name) {
		BuildingDao buildingDao = dbi.open(BuildingDao.class);
		final List<Building> buildings = buildingDao.findByNameStartingWith(name);
		buildingDao.close();
		return buildings;
	}

	public Building findById(String buildingId) {
		BuildingDao buildingDao = dbi.open(BuildingDao.class);
		final Building building = buildingDao.findById(buildingId);
		buildingDao.close();
		if(building != null) {
			findTenantsByBuildingId(buildingId).forEach(building::addTenant);
		}
		return building;
	}

	private List<Tenant> findTenantsByBuildingId(String buildingId) {
		TenantDao tenantDao = dbi.open(TenantDao.class);
		final List<Tenant> tenantsForBuilding = tenantDao.getTenantsForBuilding(buildingId);
		tenantDao.close();
		return tenantsForBuilding;
	}
}
