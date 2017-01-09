package be.insaneprogramming.cleanarch.interactor;

import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding;
import be.insaneprogramming.cleanarch.event.EventPublisher;
import be.insaneprogramming.cleanarch.event.TenantEvictedFromBuilding;
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest;

public class EvictTenantFromBuildingImpl implements EvictTenantFromBuilding {
	private final EventPublisher eventPublisher;

	public EvictTenantFromBuildingImpl(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@Override
	public void execute(EvictTenantFromBuildingRequest request) {
		eventPublisher.publish(new TenantEvictedFromBuilding(request.getBuildingId(), request.getTenantId()));
	}
}
