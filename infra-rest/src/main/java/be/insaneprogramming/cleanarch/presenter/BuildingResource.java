package be.insaneprogramming.cleanarch.presenter;

import static be.insaneprogramming.cleanarch.presenter.BuildingResource.RESOURCE_URI_TEMPLATE;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.boundary.CreateBuilding;
import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding;
import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.presenter.payloadmodel.ImmutableAddTenantToBuildingJsonPayload;
import be.insaneprogramming.cleanarch.presenter.payloadmodel.ImmutableCreateBuildingJsonPayload;
import be.insaneprogramming.cleanarch.presenter.viewmodel.BuildingJson;
import be.insaneprogramming.cleanarch.presenter.viewmodel.ImmutableBuildingJson;
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.ImmutableEvictTenantFromBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.ImmutableListBuildingsRequest;

@RestController
@RequestMapping(RESOURCE_URI_TEMPLATE)
public class BuildingResource {
	static final String RESOURCE_URI_TEMPLATE = "/building";
	static final String GET_SINGLE_URI_TEMPLATE = RESOURCE_URI_TEMPLATE + "/{id}";

	private final ListBuildings listBuildings;
	private final CreateBuilding createBuilding;
	private final AddTenantToBuilding addTenantToBuilding;
	private final EvictTenantFromBuilding evictTenantFromBuilding;

	@Inject
	public BuildingResource(ListBuildings listBuildings, CreateBuilding createBuilding, AddTenantToBuilding addTenantToBuilding, EvictTenantFromBuilding evictTenantFromBuilding) {
		this.listBuildings = listBuildings;
		this.createBuilding = createBuilding;
		this.addTenantToBuilding = addTenantToBuilding;
		this.evictTenantFromBuilding = evictTenantFromBuilding;
	}

	@PostMapping
	public ResponseEntity create(@RequestBody ImmutableCreateBuildingJsonPayload payload) {
		String id = createBuilding.execute(payload.toRequest());
		return ResponseEntity.created(new UriTemplate(GET_SINGLE_URI_TEMPLATE).expand(id).normalize()).build();
	}

	@GetMapping
	public ResponseEntity<List<BuildingJson>> list() {
		List<BuildingJson> collect = listBuildings.execute(ImmutableListBuildingsRequest.builder().build()).stream().map(it -> ImmutableBuildingJson.builder().id(it.getId()).name(it.getName()).build()).collect(Collectors.toList());
		return ResponseEntity.ok(collect);
	}

	@PostMapping("{buildingId}/tenant")
	public void addTenant(@PathVariable("buildingId") String buildingId, @RequestBody ImmutableAddTenantToBuildingJsonPayload payload) {
		addTenantToBuilding.execute(payload.toRequest(buildingId));
	}

	@DeleteMapping("{buildingId}/tenant/{tenantId}")
	public void evictTenant(@PathVariable("buildingId") String buildingId, @PathVariable("tenantId") String tenantId) {
		EvictTenantFromBuildingRequest request = ImmutableEvictTenantFromBuildingRequest.builder()
			.buildingId(buildingId)
			.tenantId(tenantId)
			.build();
		evictTenantFromBuilding.execute(request);
	}
}
