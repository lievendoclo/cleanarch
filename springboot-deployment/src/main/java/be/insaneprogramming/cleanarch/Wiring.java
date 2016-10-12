package be.insaneprogramming.cleanarch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.boundary.CreateBuilding;
import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding;
import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.entity.BuildingFactory;
import be.insaneprogramming.cleanarch.entity.TenantFactory;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.JpaBuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntityRepository;
import be.insaneprogramming.cleanarch.interactor.AddTenantToBuildingImpl;
import be.insaneprogramming.cleanarch.interactor.CreateBuildingImpl;
import be.insaneprogramming.cleanarch.interactor.EvictTenantFromBuildingImpl;
import be.insaneprogramming.cleanarch.interactor.ListBuildingsImpl;

@Configuration
public class Wiring {

	@Autowired
	private BuildingJpaEntityRepository buildingJpaEntityRepository;

	@Bean
	public AddTenantToBuilding addTenantToBuilding() {
		return new AddTenantToBuildingImpl(buildingEntityGateway(), tenantFactory());
	}

	@Bean
	public CreateBuilding createBuilding() {
		return new CreateBuildingImpl(buildingEntityGateway(), buildingFactory());
	}

	@Bean
	public EvictTenantFromBuilding evictTenantFromBuilding() {
		return new EvictTenantFromBuildingImpl(buildingEntityGateway());
	}

	@Bean
	public ListBuildings listBuildings() {
		return new ListBuildingsImpl(buildingEntityGateway());
	}

	@Bean
	public BuildingEntityGateway buildingEntityGateway() {
		return new JpaBuildingEntityGateway(buildingJpaEntityRepository, buildingFactory(), tenantFactory());
	}

	@Bean
	public BuildingFactory buildingFactory() {
		return new BuildingFactory();
	}

	@Bean
	public TenantFactory tenantFactory() {
		return new TenantFactory();
	}
}
