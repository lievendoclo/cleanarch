package be.insaneprogramming.cleanarch.boundary;

import java.util.function.Consumer;

import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;

@FunctionalInterface
@Boundary
public interface CreateBuilding {
	void execute(CreateBuildingRequest request, Consumer<String> idConsumer);
}
