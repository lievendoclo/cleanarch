package be.insaneprogramming.cleanarch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.boundary.CreateBuilding;
import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding;
import be.insaneprogramming.cleanarch.boundary.GetBuilding;
import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.presenter.JsonBuildingResponseModelPresenter;
import be.insaneprogramming.cleanarch.rest.viewmodel.BuildingJson;
import be.insaneprogramming.cleanarch.rest.viewmodel.TenantJson;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BuildingControllerRestIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;
	@MockBean
	private ListBuildings listBuildings;
	@MockBean
	private CreateBuilding createBuilding;
	@MockBean
	private AddTenantToBuilding addTenantToBuilding;
	@MockBean
	private GetBuilding getBuilding;
	@MockBean
	private EvictTenantFromBuilding evictTenantFromBuilding;

	@Test
	public void testGetBuildings() {
		// given
		List<BuildingJson> buildingJsonList = Arrays.asList(new BuildingJson("id1", "building1", Arrays.asList(new TenantJson("tid1", "tenant1"))));
		Mockito.when(listBuildings.execute(any(), any(JsonBuildingResponseModelPresenter.class))).thenReturn(buildingJsonList);

		// when
		ParameterizedTypeReference<List<BuildingJson>> typeReference = new ParameterizedTypeReference<List<BuildingJson>>() {};
		ResponseEntity<List<BuildingJson>> exchange = restTemplate.exchange("/building", HttpMethod.GET, null, typeReference);
		List<BuildingJson> returnedData = exchange.getBody();

		// then
		assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(returnedData).hasSize(1);
	}
}
