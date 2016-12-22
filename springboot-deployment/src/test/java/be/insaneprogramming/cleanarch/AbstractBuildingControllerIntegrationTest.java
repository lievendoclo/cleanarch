package be.insaneprogramming.cleanarch;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.rest.BuildingController;
import be.insaneprogramming.cleanarch.rest.payloadmodel.AddTenantToBuildingJsonPayload;
import be.insaneprogramming.cleanarch.rest.payloadmodel.CreateBuildingJsonPayload;
import be.insaneprogramming.cleanarch.rest.viewmodel.BuildingJson;
import be.insaneprogramming.cleanarch.rest.viewmodel.TenantJson;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@Transactional
public abstract class AbstractBuildingControllerIntegrationTest {
	@Autowired
	private BuildingEntityGateway buildingEntityGateway;
	@Autowired
	private BuildingController buildingController;

	@Test
	public void testGetBuildingsWithNoBuildings() throws InterruptedException {
		// when
		List<BuildingJson> buildings = buildingController.list();

		// then
		assertThat(buildings).hasSize(0);
	}

	@Test
	public void testGetBuildings() throws InterruptedException {
		// given
		buildingEntityGateway.save(new Building("id1", "building1"));
		buildingEntityGateway.save(new Building("id2", "building2"));

		// when
		List<BuildingJson> buildings = buildingController.list();

		// then
		assertThat(buildings).hasSize(2);
		assertThat(buildings).contains(new BuildingJson("id1", "building1", new ArrayList<>()));
		assertThat(buildings).contains(new BuildingJson("id2", "building2", new ArrayList<>()));
	}

	@Test
	public void testGetBuilding() throws InterruptedException {
		// given
		buildingEntityGateway.save(new Building("id1", "building1", Arrays.asList(new Tenant("tid1", "tenant1"), new Tenant("tid2", "tenant2"))));

		// when
		BuildingJson building = buildingController.get("id1");

		// then
		assertThat(building.getId()).isEqualTo("id1");
		assertThat(building.getName()).isEqualTo("building1");
		assertThat(building.getTenants()).hasSize(2);
		assertThat(building.getTenants()).containsExactly(new TenantJson("tid1", "tenant1"), new TenantJson("tid2", "tenant2"));
	}

	@Test
	public void testCreateBuilding() throws InterruptedException {
		// given
		CreateBuildingJsonPayload payload = new CreateBuildingJsonPayload("buildingName");

		// when
		ResponseEntity responseEntity = buildingController.create(payload);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		// when
		String createdId = responseEntity.getHeaders().getFirst("X-Created-Id");
		BuildingJson building = buildingController.get(createdId);

		// then
		assertThat(building.getName()).isEqualTo(payload.getName());
	}

	@Test
	public void testAddTenantToBuilding() throws InterruptedException {
		// given
		buildingEntityGateway.save(new Building("id1", "building1"));
		AddTenantToBuildingJsonPayload payload = new AddTenantToBuildingJsonPayload("tenantName");

		// when
		buildingController.addTenant("id1", payload);
		Building building = buildingEntityGateway.findById("id1");

		// then
		assertThat(building.getTenants()).hasSize(1);
	}

	@Test
	public void testEvictTenantFromBuilding() throws InterruptedException {
		// given
		buildingEntityGateway.save(new Building("id1", "building1", Arrays.asList(new Tenant("tid1", "tenant1"), new Tenant("tid2", "tenant2"))));

		// when
		buildingController.evictTenant("id1", "tid1");
		Building building = buildingEntityGateway.findById("id1");

		// then
		assertThat(building.getTenants()).hasSize(1);
	}
}
