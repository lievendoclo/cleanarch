package be.insaneprogramming.cleanarch;

import javax.sql.DataSource;

import org.skife.jdbi.v2.DBI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.boundary.CreateBuilding;
import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding;
import be.insaneprogramming.cleanarch.boundary.GetBuilding;
import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.entity.BuildingFactory;
import be.insaneprogramming.cleanarch.entity.TenantFactory;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.JdbcBuildingEntityGateway;
import be.insaneprogramming.cleanarch.interactor.AddTenantToBuildingImpl;
import be.insaneprogramming.cleanarch.interactor.CreateBuildingImpl;
import be.insaneprogramming.cleanarch.interactor.EvictTenantFromBuildingImpl;
import be.insaneprogramming.cleanarch.interactor.GetBuildingImpl;
import be.insaneprogramming.cleanarch.interactor.ListBuildingsImpl;
import be.insaneprogramming.cleanarch.rest.BuildingResource;

@Configuration
public class Wiring {
	@Bean
	public AddTenantToBuilding addTenantToBuilding(BuildingEntityGateway buildingEntityGateway, TenantFactory tenantFactory) {
		return new AddTenantToBuildingImpl(buildingEntityGateway, tenantFactory);
	}

	@Bean
	public CreateBuilding createBuilding(BuildingEntityGateway buildingEntityGateway, BuildingFactory buildingFactory) {
		return new CreateBuildingImpl(buildingFactory, buildingEntityGateway);
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
	public BuildingFactory buildingFactory()  {
		return new BuildingFactory();
	}

	@Bean
	public TenantFactory tenantFactory()  {
		return new TenantFactory();
	}

	@Bean
	public DBI dbi(DataSource dataSource) {
		return new DBI(dataSource);
	}

	@Bean
	public BuildingEntityGateway buildingEntityGateway(DBI dbi)  {
		return new JdbcBuildingEntityGateway(dbi);
	}

	@Bean
	public BuildingResource buildingResource(AddTenantToBuilding addTenantToBuilding, EvictTenantFromBuilding evictTenantFromBuilding, CreateBuilding createBuilding, ListBuildings listBuildings, GetBuilding getBuilding) {
		return new BuildingResource(addTenantToBuilding, createBuilding, evictTenantFromBuilding, listBuildings, getBuilding);
	}

	@Bean
	public RestApplication restApplication(BuildingResource buildingResource) {
		return new RestApplication(buildingResource);
	}
}