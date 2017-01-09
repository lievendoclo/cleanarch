package be.insaneprogramming.cleanarch.interactor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import be.insaneprogramming.cleanarch.boundary.CreateBuilding;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;

public class CreateBuildingImplTest {
	private CreateBuilding createBuilding;
	private BuildingEntityGateway buildingEntityGateway;

	@Before
	public void initializeComponentUnderTest() {
		buildingEntityGateway = mock(BuildingEntityGateway.class);
		createBuilding = new CreateBuildingImpl(eventPublisher);
	}

	@Test
	public void shouldAddCreateBuilding() {
		//given
		ArgumentCaptor<Building> buildingArg = ArgumentCaptor.forClass(Building.class);
		doReturn("buildingId").when(buildingEntityGateway).save(buildingArg.capture());
		CreateBuildingRequest request = new CreateBuildingRequest("buildingName");

		//when
		String buildingId = createBuilding.execute(request);

		//then
		assertThat(buildingId).isEqualTo("buildingId");
		assertThat(buildingArg.getValue().getName()).isEqualTo("buildingName");
	}
}
