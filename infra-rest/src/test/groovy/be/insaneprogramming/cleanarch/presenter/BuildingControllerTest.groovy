package be.insaneprogramming.cleanarch.presenter

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding
import be.insaneprogramming.cleanarch.boundary.CreateBuilding
import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding
import be.insaneprogramming.cleanarch.boundary.ListBuildings
import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest
import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel
import be.insaneprogramming.cleanarch.rest.BuildingController
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import com.jayway.restassured.module.mockmvc.config.AsyncConfig
import spock.lang.Specification

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.function.Supplier

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given
import static org.hamcrest.Matchers.equalTo

class BuildingControllerTest extends Specification {
	BuildingController buildingResource
	ListBuildings listBuildings
	CreateBuilding createBuilding
	AddTenantToBuilding addTenantToBuilding
	EvictTenantFromBuilding evictTenantFromBuilding

	def setup() {
		listBuildings = Mock()
		createBuilding = Mock()
		addTenantToBuilding = Mock()
		evictTenantFromBuilding = Mock()
		buildingResource = new BuildingController(addTenantToBuilding, createBuilding, evictTenantFromBuilding, listBuildings)
		RestAssuredMockMvc.standaloneSetup(buildingResource)
		RestAssuredMockMvc.config().asyncConfig(AsyncConfig.withTimeout(1000, TimeUnit.MILLISECONDS))
	}

	def "returns a list of buildings"() {
		given:
		listBuildings.execute(_ as ListBuildingsRequest, _ as JsonBuildingListPresenter) >> CompletableFuture.supplyAsync(new Supplier() {
			@Override
			Object get() {
				[new BuildingResponseModel('testId', "testName", [])]
			}
		})

		when:
		def response = given().when().async().get("/building").then()

		then:
		response.log().all(true)
		response.statusCode(200)
		response.body("\$.size()", equalTo(1))
		response.body("[0].id", equalTo("testId"))
		response.body("[0].name", equalTo("testName"))
	}

	def "create a building"() {
		given:
		createBuilding.execute(new CreateBuildingRequest('test')) >> "testId"

		when:
		def response = given()
				.body([name: 'test'])
				.contentType("application/json")
				.post("/building").then()

		then:
		response.log().all(true)
		response.statusCode(201)
		response.header("Location", "/building/testId")
	}

	def "evict a tenant from a building"() {
		when:
		def response = given()
				.delete("/building/testId/tenant/tenantId").then()

		then:
		response.log().all(true)
		response.statusCode(200)
		1 * evictTenantFromBuilding.execute(new EvictTenantFromBuildingRequest('testId', 'tenantId'))
	}

	def "add a tenant to a building"() {
		when:
		def response = given()
				.body([name: 'test'])
				.contentType("application/json")
				.post("/building/testId/tenant").then()

		then:
		response.log().all(true)
		response.statusCode(201)
		1 * addTenantToBuilding.execute(new AddTenantToBuildingRequest('testId', 'test'))
	}

}
