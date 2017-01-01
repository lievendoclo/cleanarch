package be.insaneprogramming.cleanarch;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.RestAssured;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BuildingIntegrationTest {
	@Autowired
	private BuildingEntityGateway buildingEntityGateway;
	@Value("${local.server.port}")
	private int serverPort;

	@Before
	public void setUp() throws Exception {
		RestAssured.port = serverPort;
	}

	@Test
	public void testGetBuildingsWithNoBuildings() throws InterruptedException {
		// expect
		when()
				.get("/building?nameStartsWith=nehnehneh")
				.then()
				.statusCode(200)
				.body("size()", is(0));
	}

	@Test
	public void testGetAllBuildings() throws InterruptedException {
		// given
		buildingEntityGateway.save(new Building("id1", "building1"));
		buildingEntityGateway.save(new Building("id2", "building2"));

		// expect
		when()
				.get("/building")
				.then()
				.statusCode(200)
				.body("size()", greaterThan(2));
	}

	@Test
	public void testGetBuildingsByName() throws InterruptedException {
		// given
		String nameBuilding1 = UUID.randomUUID().toString();
		String nameBuilding2 = UUID.randomUUID().toString();
		buildingEntityGateway.save(new Building("id1", nameBuilding1));
		buildingEntityGateway.save(new Building("id2", nameBuilding2));

		// expect
		when()
				.get("/building?nameStartsWith=" + nameBuilding1)
				.then()
				.statusCode(200)
				.body("size()", greaterThan(1));
	}

	@Test
	public void testGetBuilding() throws InterruptedException {
		// given
		final String building1 = UUID.randomUUID().toString();
		final String id1 = UUID.randomUUID().toString();
		buildingEntityGateway.save(new Building(id1, building1, Arrays.asList(new Tenant("tid1", "tenant1"), new Tenant("tid2", "tenant2"))));

		// expect
		// expect
		when()
				.get("/building/" + id1)
				.then()
				.statusCode(200)
				.body("id", is(id1))
				.body("name", is(building1))
				.body("tenants.size()", is(2));
	}

	@Test
	public void testCreateBuilding() throws InterruptedException {
		// given
		String payload = "{\"name\":\"testBuilding\"}";

		// expect
		String createdId = given()
				.body(payload)
				.contentType("application/json")
				.when()
				.log().all()
				.post("/building")
				.then()
				.statusCode(201)
				.extract().header("X-Created-Id");
		// and
		when()
				.get("/building/" + createdId)
				.then()
				.statusCode(200)
				.body("name", is("testBuilding"));

	}

	@Test
	public void testAddTenantToBuilding() throws InterruptedException {
		// given
		final String buildingId = "id1";
		buildingEntityGateway.save(new Building(buildingId, "building1"));
		String payload = "{\"name\":\"testTenant\"}";

		// expect
		when()
				.get("/building/{0}", buildingId)
				.then()
				.statusCode(200)
				.body("tenants.size()", is(0));

		given()
				.body(payload)
				.contentType("application/json")
				.when()
				.post("/building/{0}/tenant", buildingId)
				.then()
				.statusCode(201);
		// and
		when()
				.get("/building/{0}", buildingId)
				.then()
				.statusCode(200)
				.body("tenants.size()", is(1))
				.body("tenants[0].name", is("testTenant"));
	}

	@Test
	public void testEvictTenantFromBuilding() throws InterruptedException {
		// given
		final String buildingId = "testEvictTenantFromBuilding-id1";
		final String tenantId1 = "testEvictTenantFromBuilding-tid1";
		final String tenant2Name = "testEvictTenantFromBuilding-tenant2";
		buildingEntityGateway.save(new Building(buildingId, "testEvictTenantFromBuilding-building1", Arrays.asList(new Tenant(tenantId1, "testEvictTenantFromBuilding-tenant1"), new Tenant("testEvictTenantFromBuilding-tid2",
				tenant2Name))));

		// expect
		when()
				.get("/building/{0}", buildingId)
				.then()
				.statusCode(200)
				.body("tenants.size()", is(2));

		when()
				.delete("/building/{0}/tenant/{1}", buildingId, tenantId1)
				.then()
				.statusCode(200);
		// and
		when()
				.get("/building/{0}", buildingId)
				.then()
				.statusCode(200)
				.body("tenants.size()", is(1))
				.body("tenants[0].name", is(tenant2Name));
	}
}
