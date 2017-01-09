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

	public List<Building> findAll() {
		try(BuildingDao buildingDao = dbi.open(BuildingDao.class)) {
			return buildingDao.findAll();
		}
	}

	@Override
	public List<Building> findByNameStartingWith(String name) {
		try(BuildingDao buildingDao = dbi.open(BuildingDao.class)) {
			final List<Building> buildings = buildingDao.findByNameStartingWith(name);
			return buildings;
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
			final List<Tenant> tenantsForBuilding = tenantDao.getTenantsForBuilding(buildingId);
			return tenantsForBuilding;
		}
	}
}
