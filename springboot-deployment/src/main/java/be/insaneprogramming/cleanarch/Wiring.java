package be.insaneprogramming.cleanarch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.boundary.CreateBuilding;
import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding;
import be.insaneprogramming.cleanarch.boundary.GetBuilding;
import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.JpaBuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntityRepository;
import be.insaneprogramming.cleanarch.interactor.AddTenantToBuildingImpl;
import be.insaneprogramming.cleanarch.interactor.CreateBuildingImpl;
import be.insaneprogramming.cleanarch.interactor.EvictTenantFromBuildingImpl;
import be.insaneprogramming.cleanarch.interactor.GetBuildingImpl;
import be.insaneprogramming.cleanarch.interactor.ListBuildingsImpl;
import be.insaneprogramming.cleanarch.rest.BuildingResource;

@Configuration
public class Wiring {
	@Bean
	public AddTenantToBuilding addTenantToBuilding(BuildingEntityGateway buildingEntityGateway) {
		return new AddTenantToBuildingImpl(buildingEntityGateway);
	}

	@Bean
	public CreateBuilding createBuilding(BuildingEntityGateway buildingEntityGateway) {
		return new CreateBuildingImpl(buildingEntityGateway);
	}

	@Bean
	public EvictTenantFromBuilding evictTenantFromBuilding( BuildingEntityGateway buildingEntityGateway)  {
		return new EvictTenantFromBuildingImpl(buildingEntityGateway);
	}

	@Bean
	public ListBuildings listBuildings(BuildingEntityGateway buildingEntityGateway)  {
		return new ListBuildingsImpl(buildingEntityGateway);
	}

	@Bean
	public GetBuilding getBuilding(BuildingEntityGateway buildingEntityGateway)  {
		return new GetBuildingImpl(buildingEntityGateway);
	}

	@Bean
	public BuildingEntityGateway buildingEntityGateway(BuildingJpaEntityRepository buildingJpaEntityRepository)  {
		return new JpaBuildingEntityGateway(buildingJpaEntityRepository);
	}

	@Bean
	public BuildingResource buildingResource(CreateBuilding createBuilding, AddTenantToBuilding addTenantToBuilding, EvictTenantFromBuilding evictTenantFromBuilding, ListBuildings listBuildings, GetBuilding getBuilding) {
		return new BuildingResource(addTenantToBuilding, createBuilding, evictTenantFromBuilding, listBuildings, getBuilding);
	}

	@Bean
	public RestApplication restApplication(BuildingResource buildingResource) {
		return new RestApplication(buildingResource);
	}
}
