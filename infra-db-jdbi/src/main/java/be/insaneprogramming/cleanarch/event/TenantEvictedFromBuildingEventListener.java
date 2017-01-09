package be.insaneprogramming.cleanarch.event;

import org.skife.jdbi.v2.DBI;

import be.insaneprogramming.cleanarch.entitygatewayimpl.jdbi.TenantDao;

public class TenantEvictedFromBuildingEventListener implements EventListener<TenantEvictedFromBuilding> {
	private DBI dbi;

	public TenantEvictedFromBuildingEventListener(DBI dbi) {
		this.dbi = dbi;
	}

	@Override
	public Class<TenantEvictedFromBuilding> getEventClass() {
		return TenantEvictedFromBuilding.class;
	}

	@Override
	public void onEvent(TenantEvictedFromBuilding event) {
		try(TenantDao tenantDao = dbi.open(TenantDao.class)) {
			tenantDao.delete(event.getBuildingId(), event.getBuildingId());
		}
	}
}
