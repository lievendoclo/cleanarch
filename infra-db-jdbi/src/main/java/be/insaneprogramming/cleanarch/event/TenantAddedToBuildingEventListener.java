package be.insaneprogramming.cleanarch.event;

import org.skife.jdbi.v2.DBI;

import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entity.TenantFactory;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jdbi.TenantDao;

public class TenantAddedToBuildingEventListener implements EventListener<TenantAddedToBuilding> {
	private DBI dbi;

	public TenantAddedToBuildingEventListener(DBI dbi) {
		this.dbi = dbi;
	}

	@Override
	public Class<TenantAddedToBuilding> getEventClass() {
		return TenantAddedToBuilding.class;
	}

	@Override
	public void onEvent(TenantAddedToBuilding event) {
		try(TenantDao tenantDao = dbi.open(TenantDao.class)) {
			final Tenant tenant = TenantFactory.create().createTenant(event.getTenantId(), event.getName());
			tenantDao.insert(event.getBuildingId(), tenant);
		}
	}
}
