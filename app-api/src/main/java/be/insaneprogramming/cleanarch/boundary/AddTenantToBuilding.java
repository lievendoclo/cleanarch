package be.insaneprogramming.cleanarch.boundary;

import java.util.function.Consumer;

import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;

@FunctionalInterface
@Boundary
public interface AddTenantToBuilding {
	void execute(AddTenantToBuildingRequest request, Consumer<String> tenantIdConsumer);
}
