package be.insaneprogramming.cleanarch.entitygatewayimpl;

import java.util.List;

import javax.inject.Named;
import javax.sql.DataSource;

import org.skife.jdbi.v2.DBI;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jdbi.BuildingDao;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jdbi.TenantDao;

@Named
public class JdbcBuildingEntityGateway implements BuildingEntityGateway {
	private final DBI dbi;

	public JdbcBuildingEntityGateway(DataSource dataSource) {
		this.dbi = new DBI(dataSource);
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
		try(BuildingDao buildingDao = dbi.open(BuildingDao.class)) {
			buildingDao.insert(building);
			saveTenants(building);
			return building.getId();
		}
	}

	private String update(Building building) {
		try(BuildingDao buildingDao = dbi.open(BuildingDao.class)) {
			buildingDao.update(building);
			saveTenants(building);
			return building.getId();
		}
	}

	private void saveTenants(Building building) {
		try(TenantDao tenantDao = dbi.open(TenantDao.class)) {
			tenantDao.deleteAllForBuilding(building.getId());
			building.getTenants().forEach(it -> insert(building.getId(), it));
		}
	}

	private String insert(String buildingId, Tenant tenant) {
		try(TenantDao tenantDao = dbi.open(TenantDao.class)) {
			tenantDao.insert(buildingId, tenant);
			return tenant.getId();
		}
	}

	public List<Building> findAll() {
		try(BuildingDao buildingDao = dbi.open(BuildingDao.class)) {
			return buildingDao.findAll();
		}
	}

	@Override
	public List<Building> findByNameStartingWith(String name) {
		try(BuildingDao buildingDao = dbi.open(BuildingDao.class)) {
			return buildingDao.findByNameStartingWith(name);
		}
	}

	public Building findById(String buildingId) {
		try(BuildingDao buildingDao = dbi.open(BuildingDao.class)) {
			final Building building = buildingDao.findById(buildingId);
			if (building != null) {
				findTenantsByBuildingId(buildingId).forEach(building::addTenant);
			}
			return building;
		}
	}

	private List<Tenant> findTenantsByBuildingId(String buildingId) {
		try(TenantDao tenantDao = dbi.open(TenantDao.class)) {
			return tenantDao.getTenantsForBuilding(buildingId);
		}
	}
}
