package be.insaneprogramming.cleanarch;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.boundary.CreateBuilding;
import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding;
import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.entity.BuildingFactory;
import be.insaneprogramming.cleanarch.entity.TenantFactory;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.JdbcBuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.JpaBuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.MongoDbBuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntityRepository;
import be.insaneprogramming.cleanarch.interactor.AddTenantToBuildingImpl;
import be.insaneprogramming.cleanarch.interactor.CreateBuildingImpl;
import be.insaneprogramming.cleanarch.interactor.EvictTenantFromBuildingImpl;
import be.insaneprogramming.cleanarch.interactor.ListBuildingsImpl;

@Configuration
public class Wiring {

	@Bean
	public AddTenantToBuilding addTenantToBuilding(BuildingEntityGateway buildingEntityGateway) {
		return new AddTenantToBuildingImpl(buildingEntityGateway, tenantFactory());
	}

	@Bean
	public CreateBuilding createBuilding(BuildingEntityGateway buildingEntityGateway) {
		return new CreateBuildingImpl(buildingEntityGateway, buildingFactory());
	}

	@Bean
	public EvictTenantFromBuilding evictTenantFromBuilding(BuildingEntityGateway buildingEntityGateway) {
		return new EvictTenantFromBuildingImpl(buildingEntityGateway);
	}

	@Bean
	public ListBuildings listBuildings(BuildingEntityGateway buildingEntityGateway) {
		return new ListBuildingsImpl(buildingEntityGateway);
	}

	@Bean
	public BuildingFactory buildingFactory() {
		return new BuildingFactory();
	}

	@Bean
	public TenantFactory tenantFactory() {
		return new TenantFactory();
	}

	@Configuration
	@Profile("jpa")
	public static class JpaConfiguration {
		@Autowired
		private BuildingJpaEntityRepository buildingJpaEntityRepository;

		@Bean
		public BuildingEntityGateway buildingEntityGateway(BuildingFactory buildingFactory, TenantFactory tenantFactory) {
			return new JpaBuildingEntityGateway(buildingJpaEntityRepository, buildingFactory, tenantFactory);
		}
	}

	@Configuration
	@Profile("jdbc")
	public static class JdbcConfiguration {
		@Bean
		public BuildingEntityGateway buildingEntityGateway(NamedParameterJdbcTemplate jdbcTemplate) {
			return new JdbcBuildingEntityGateway(jdbcTemplate);
		}
	}

	@Configuration
	@Profile("mongodb")
	public static class MongoConfiguration {
		@Bean
		public BuildingEntityGateway buildingEntityGateway(Datastore datastore) {
			return new MongoDbBuildingEntityGateway(datastore);
		}

		@Bean
		public MongoClient mongo() {
			return new Fongo("cleanarch").getMongo();
		}

		@Bean
		public Datastore datastore(MongoClient mongoClient) {
			Morphia morphia = new Morphia();
			morphia.mapPackage("be.insaneprogramming.cleanarch.entitygatewayimpl.morphia");
			Datastore datastore = morphia.createDatastore(mongoClient, "cleanarch");
			datastore.ensureIndexes();
			return datastore;
		}
	}
}
