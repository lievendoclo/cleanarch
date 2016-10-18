package be.insaneprogramming.cleanarch.presenter

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding
import be.insaneprogramming.cleanarch.boundary.CreateBuilding
import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding
import be.insaneprogramming.cleanarch.boundary.ListBuildings
import be.insaneprogramming.cleanarch.requestmodel.ImmutableAddTenantToBuildingRequest
import be.insaneprogramming.cleanarch.requestmodel.ImmutableCreateBuildingRequest
import be.insaneprogramming.cleanarch.requestmodel.ImmutableEvictTenantFromBuildingRequest
import be.insaneprogramming.cleanarch.requestmodel.ImmutableListBuildingsRequest
import be.insaneprogramming.cleanarch.responsemodel.ImmutableBuildingResponseModel
import be.insaneprogramming.cleanarch.rest.BuildingController
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import spock.lang.Specification

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.get
import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given
import static org.hamcrest.CoreMatchers.equalTo

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
		buildingResource = new BuildingController(listBuildings, createBuilding, addTenantToBuilding, evictTenantFromBuilding)
		RestAssuredMockMvc.standaloneSetup(buildingResource);
	}

	def "returns a list of buildings"() {
		given:
		listBuildings.execute(ImmutableListBuildingsRequest.builder().build()) >> [ImmutableBuildingResponseModel.builder().id("testId").name("testName").build()]

		when:
		def response = get("/building").then()

		then:
		response.log().all(true)
		response.statusCode(200)
		response.body("\$.size()", equalTo(1))
		response.body("[0].id", equalTo("testId"))
		response.body("[0].name", equalTo("testName"))
	}

	def "create a building"() {
		given:
		createBuilding.execute(ImmutableCreateBuildingRequest.of('test')) >> "testId"

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
		1 * evictTenantFromBuilding.execute(ImmutableEvictTenantFromBuildingRequest.of('testId', 'tenantId'))
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
		1 * addTenantToBuilding.execute(ImmutableAddTenantToBuildingRequest.of('testId', 'test'))
	}
}
