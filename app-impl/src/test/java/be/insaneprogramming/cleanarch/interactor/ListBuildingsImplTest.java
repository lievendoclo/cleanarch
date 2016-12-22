package be.insaneprogramming.cleanarch.interactor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;

public class ListBuildingsImplTest {
	private ListBuildings listBuildings;
	private BuildingEntityGateway buildingEntityGateway;

	@Before
	public void initializeComponentUnderTest() {
		buildingEntityGateway = mock(BuildingEntityGateway.class);
		listBuildings = new ListBuildingsImpl(buildingEntityGateway);
	}

	@Test
	public void shouldAddCreateBuilding() {
		//given
		List<Building> buildingList = new ArrayList<>();
		buildingList.add(new Building("buildingId", "buildingName"));
		doReturn(buildingList).when(buildingEntityGateway).findAll();
		ListBuildingsRequest request = new ListBuildingsRequest();

		//when
		List<String> buildings = listBuildings.execute(request, BuildingResponseModel::getId);

		//then
		assertThat(buildings).hasSize(1);
	}
}
