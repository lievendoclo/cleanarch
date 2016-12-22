package be.insaneprogramming.cleanarch;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK)
@DirtiesContext
@Transactional
public abstract class AbstractBuildingControllerIntegrationTest {
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private BuildingEntityGateway buildingEntityGateway;
//	@Autowired
//	private BuildingController buildingController;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		RestAssuredMockMvc.mockMvc(mockMvc);
	}

	@Test
	public void testGetBuildingsWithNoBuildings() throws InterruptedException {
		// expect
		when()
				.get("/building")
				.then()
				.statusCode(200)
				.body("size()", is(0));
	}

	@Test
	public void testGetBuildings() throws InterruptedException {
		// given
		buildingEntityGateway.save(new Building("id1", "building1"));
		buildingEntityGateway.save(new Building("id2", "building2"));

		// expect
		when()
				.get("/building")
				.then()
				.statusCode(200)
				.body("size()", is(2))
				.body("[0].id", is("id1"))
				.body("[0].name", is("building1"))
				.body("[1].id", is("id2"))
				.body("[1].name", is("building2"));
	}

	@Test
	public void testGetBuilding() throws InterruptedException {
		// given
		buildingEntityGateway.save(new Building("id1", "building1", Arrays.asList(new Tenant("tid1", "tenant1"), new Tenant("tid2", "tenant2"))));

		// expect
		// expect
		when()
				.get("/building/id1")
				.then()
				.statusCode(200)
				.body("id", is("id1"))
				.body("name", is("building1"))
				.body("tenants.size()", is(2));
	}

	@Test
	public void testCreateBuilding() throws InterruptedException {
		// given
		String payload = "{\"name\":\"testBuilding\"}";

		AtomicReference<String> createdId = new AtomicReference<>();
		// expect
		given()
				.body(payload)
				.contentType("application/json")
				.when()
				.post("/building")
				.then()
				.statusCode(201)
				.apply(new ResultHandler() {
					@Override
					public void handle(MvcResult result) throws Exception {
						String id = result.getResponse().getHeader("X-Created-Id");
						createdId.set(id);
					}
				});
		// and
		when()
				.get("/building/" + createdId.get())
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
		final String buildingId = "id1";
		final String tenantId1 = "tid1";
		buildingEntityGateway.save(new Building(buildingId, "building1", Arrays.asList(new Tenant(tenantId1, "tenant1"), new Tenant("tid2", "tenant2"))));

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
				.body("tenants[0].name", is("tenant2"));
	}
}
