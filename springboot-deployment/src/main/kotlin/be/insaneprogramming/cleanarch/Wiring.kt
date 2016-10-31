package be.insaneprogramming.cleanarch

import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Morphia
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.fakemongo.Fongo
import com.mongodb.MongoClient

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding
import be.insaneprogramming.cleanarch.boundary.CreateBuilding
import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding
import be.insaneprogramming.cleanarch.boundary.ListBuildings
import be.insaneprogramming.cleanarch.entity.BuildingFactory
import be.insaneprogramming.cleanarch.entity.TenantFactory
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.entitygatewayimpl.JdbcBuildingEntityGateway
import be.insaneprogramming.cleanarch.entitygatewayimpl.JpaBuildingEntityGateway
import be.insaneprogramming.cleanarch.entitygatewayimpl.MongoDbBuildingEntityGateway
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntityRepository
import be.insaneprogramming.cleanarch.interactor.AddTenantToBuildingImpl
import be.insaneprogramming.cleanarch.interactor.CreateBuildingImpl
import be.insaneprogramming.cleanarch.interactor.EvictTenantFromBuildingImpl
import be.insaneprogramming.cleanarch.interactor.ListBuildingsImpl

@Configuration
open class Wiring {

    @Bean
    open fun addTenantToBuilding(buildingEntityGateway: BuildingEntityGateway): AddTenantToBuilding {
        return AddTenantToBuildingImpl(buildingEntityGateway, tenantFactory())
    }

    @Bean
    open fun createBuilding(buildingEntityGateway: BuildingEntityGateway): CreateBuilding {
        return CreateBuildingImpl(buildingEntityGateway, buildingFactory())
    }

    @Bean
    open fun evictTenantFromBuilding(buildingEntityGateway: BuildingEntityGateway): EvictTenantFromBuilding {
        return EvictTenantFromBuildingImpl(buildingEntityGateway)
    }

    @Bean
    open fun listBuildings(buildingEntityGateway: BuildingEntityGateway): ListBuildings {
        return ListBuildingsImpl(buildingEntityGateway)
    }

    @Bean
    open fun buildingFactory(): BuildingFactory {
        return BuildingFactory()
    }

    @Bean
    open fun tenantFactory(): TenantFactory {
        return TenantFactory()
    }

    @Bean
    open fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.registerModule(KotlinModule())
        return mapper
    }

    @Configuration
    @Profile("jpa")
    open class JpaConfiguration {
        @Autowired
        private val buildingJpaEntityRepository: BuildingJpaEntityRepository? = null

        @Bean
        open fun buildingEntityGateway(buildingFactory: BuildingFactory, tenantFactory: TenantFactory): BuildingEntityGateway {
            return JpaBuildingEntityGateway(buildingJpaEntityRepository!!, buildingFactory, tenantFactory)
        }
    }

    @Configuration
    @Profile("jdbc")
    open class JdbcConfiguration {
        @Bean
        open fun buildingEntityGateway(jdbcTemplate: NamedParameterJdbcTemplate): BuildingEntityGateway {
            return JdbcBuildingEntityGateway(jdbcTemplate)
        }
    }

    @Configuration
    @Profile("mongodb")
    open class MongoConfiguration {
        @Bean
        open fun buildingEntityGateway(datastore: Datastore): BuildingEntityGateway {
            return MongoDbBuildingEntityGateway(datastore)
        }

        @Bean
        open fun mongo(): MongoClient {
            return Fongo("cleanarch").mongo
        }

        @Bean
        open fun datastore(mongoClient: MongoClient): Datastore {
            val morphia = Morphia()
            morphia.mapPackage("be.insaneprogramming.cleanarch.entitygatewayimpl.morphia")
            val datastore = morphia.createDatastore(mongoClient, "cleanarch")
            datastore.ensureIndexes()
            return datastore
        }
    }
}
