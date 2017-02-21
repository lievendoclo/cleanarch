package be.insaneprogramming.cleanarch.interactor;

import java.util.function.Consumer;

import javax.inject.Named;
import javax.transaction.Transactional;

import be.insaneprogramming.cleanarch.boundary.CreateBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingFactory;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;

@Transactional
@Named
public class CreateBuildingImpl implements CreateBuilding {

	private final BuildingEntityGateway buildingEntityGateway;

	public CreateBuildingImpl(BuildingEntityGateway buildingEntityGateway) {
		this.buildingEntityGateway = buildingEntityGateway;
	}

	@Override
	public void execute(CreateBuildingRequest request, Consumer<String> idConsumer) {
		Building building = BuildingFactory.create().createBuilding(request.getName());
		idConsumer.accept(buildingEntityGateway.save(building));
	}
}
