package be.insaneprogramming.cleanarch.interactor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingFactory;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;

public class AddTenantToBuildingImplTest {

	private AddTenantToBuilding addTenantToBuilding;
	private BuildingEntityGateway buildingEntityGateway;

	@Before
	public void initializeComponentUnderTest() {
		buildingEntityGateway = mock(BuildingEntityGateway.class);
		addTenantToBuilding = new AddTenantToBuildingImpl(buildingEntityGateway);
	}
	
	@Test
	public void shouldAddTenantToBuilding() {
		//given
		Building building = BuildingFactory.create().createBuilding("buildingId", "test");
		doReturn(building).when(buildingEntityGateway).findById("buildingId");
		AddTenantToBuildingRequest request = new AddTenantToBuildingRequest("buildingId", "tenantName");

 		//when
		addTenantToBuilding.execute(request, s -> {});

		//then
		assertThat(building.getTenants()).hasSize(1);
	}
}
