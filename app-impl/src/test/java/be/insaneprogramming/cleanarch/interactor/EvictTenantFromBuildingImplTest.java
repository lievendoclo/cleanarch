package be.insaneprogramming.cleanarch.interactor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest;

public class EvictTenantFromBuildingImplTest {
	private EvictTenantFromBuilding evictTenantFromBuilding;
	private BuildingEntityGateway buildingEntityGateway;

	@Before
	public void initializeComponentUnderTest() {
		buildingEntityGateway = mock(BuildingEntityGateway.class);
		evictTenantFromBuilding = new EvictTenantFromBuildingImpl(buildingEntityGateway);
	}

	@Test
	public void shouldEvictTenantFromBuilding() {
		//given
		List<Tenant> existingTenants = new ArrayList<>();
		existingTenants.add(new Tenant("tenantId", "John Doe"));
		Building building = new Building("buildingId", "test", existingTenants);
		doReturn(building).when(buildingEntityGateway).findById("buildingId");
		EvictTenantFromBuildingRequest request = new EvictTenantFromBuildingRequest("buildingId", "tenantId");

		//when
		evictTenantFromBuilding.execute(request);

		//then
		assertThat(building.getTenants()).hasSize(0);
	}
}
