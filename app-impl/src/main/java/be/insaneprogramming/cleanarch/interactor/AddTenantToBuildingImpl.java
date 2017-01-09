package be.insaneprogramming.cleanarch.interactor;

import java.util.UUID;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.event.EventPublisher;
import be.insaneprogramming.cleanarch.event.TenantAddedToBuilding;
import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;

public class AddTenantToBuildingImpl implements AddTenantToBuilding {

	private final EventPublisher eventPublisher;

	public AddTenantToBuildingImpl(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@Override
	public String execute(AddTenantToBuildingRequest request) {
		String id = UUID.randomUUID().toString();
		eventPublisher.publish(new TenantAddedToBuilding(request.getBuildingId(), id, request.getTenantName()));
		return id;
	}
}
