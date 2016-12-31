package be.insaneprogramming.cleanarch.rest;

import static be.insaneprogramming.cleanarch.rest.BuildingController.RESOURCE_URI_TEMPLATE;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.boundary.CreateBuilding;
import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding;
import be.insaneprogramming.cleanarch.boundary.GetBuilding;
import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.presenter.JsonBuildingResponseModelPresenter;
import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.GetBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;
import be.insaneprogramming.cleanarch.rest.payloadmodel.AddTenantToBuildingJsonPayload;
import be.insaneprogramming.cleanarch.rest.payloadmodel.CreateBuildingJsonPayload;
import be.insaneprogramming.cleanarch.rest.viewmodel.BuildingJson;

@RestController
@RequestMapping(RESOURCE_URI_TEMPLATE)
public class BuildingController {
	static final String RESOURCE_URI_TEMPLATE = "/building";
	private static final String GET_SINGLE_BUILDING_URI_TEMPLATE = RESOURCE_URI_TEMPLATE + "/{buildingId}";
	private static final String GET_BUILDING_TENANT_URI_TEMPLATE = GET_SINGLE_BUILDING_URI_TEMPLATE + "/tenant/{tenantId}";

	private AddTenantToBuilding addTenantToBuilding;
	private CreateBuilding createBuilding;
	private EvictTenantFromBuilding evictTenantFromBuilding;
	private ListBuildings listBuildings;
	private GetBuilding getBuilding;

	public BuildingController(AddTenantToBuilding addTenantToBuilding, CreateBuilding createBuilding, EvictTenantFromBuilding evictTenantFromBuilding,
			ListBuildings listBuildings, GetBuilding getBuilding) {
		this.addTenantToBuilding = addTenantToBuilding;
		this.createBuilding = createBuilding;
		this.evictTenantFromBuilding = evictTenantFromBuilding;
		this.listBuildings = listBuildings;
		this.getBuilding = getBuilding;
	}

	@PostMapping
	public ResponseEntity create(@RequestBody CreateBuildingJsonPayload payload) {
		String id = createBuilding.execute(new CreateBuildingRequest(payload.getName()));
		return ResponseEntity.created(new UriTemplate(GET_SINGLE_BUILDING_URI_TEMPLATE).expand(id).normalize())
				.header("X-Created-Id", id)
				.build();
	}

	@GetMapping
	public List<BuildingJson> find(@RequestParam(value = "nameStartsWith", required = false) String name)  {
		return listBuildings.execute(new ListBuildingsRequest(name), new JsonBuildingResponseModelPresenter());
	}

	@GetMapping("/{buildingId}")
	public BuildingJson get(@PathVariable String buildingId)  {
		return getBuilding.execute(new GetBuildingRequest(buildingId), new JsonBuildingResponseModelPresenter());
	}

	@PostMapping("{buildingId}/tenant")
	public ResponseEntity addTenant(@PathVariable("buildingId") String buildingId, @RequestBody AddTenantToBuildingJsonPayload payload) {
		String id = addTenantToBuilding.execute(new AddTenantToBuildingRequest(buildingId, payload.getName()));
		return ResponseEntity.created(new UriTemplate(GET_BUILDING_TENANT_URI_TEMPLATE).expand(buildingId, id).normalize()).build();
	}

	@DeleteMapping("{buildingId}/tenant/{tenantId}")
	public void evictTenant(@PathVariable("buildingId") String buildingId, @PathVariable("tenantId") String tenantId) {
		evictTenantFromBuilding.execute(new EvictTenantFromBuildingRequest(buildingId, tenantId));
	}
}
