package be.insaneprogramming.cleanarch.interactor;

import java.util.UUID;

import be.insaneprogramming.cleanarch.boundary.CreateBuilding;
import be.insaneprogramming.cleanarch.event.BuildingCreated;
import be.insaneprogramming.cleanarch.event.EventPublisher;
import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;

public class CreateBuildingImpl implements CreateBuilding {

	private final EventPublisher eventPublisher;

	public CreateBuildingImpl(EventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@Override
	public String execute(CreateBuildingRequest request) {
		String id = UUID.randomUUID().toString();
		eventPublisher.publish(new BuildingCreated(id, request.getName()));
		return id;
	}
}
