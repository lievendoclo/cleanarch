package be.insaneprogramming.cleanarch.rest;

import static be.insaneprogramming.cleanarch.rest.BuildingController.RESOURCE_URI_TEMPLATE;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
import be.insaneprogramming.cleanarch.boundary.GetBuilding;
import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.presenter.JsonBuildingResponseModelListPresenter;
import be.insaneprogramming.cleanarch.presenter.JsonBuildingResponseModelPresenter;
import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.GetBuildingRequest;
import be.insaneprogramming.cleanarch.rest.payloadmodel.AddTenantToBuildingJsonPayload;
import be.insaneprogramming.cleanarch.rest.payloadmodel.CreateBuildingJsonPayload;
import be.insaneprogramming.cleanarch.rest.requestparam.ListBuildingsRequestParams;
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
		final AtomicReference<String> id = new AtomicReference<>();
		createBuilding.execute(new CreateBuildingRequest(payload.getName()), id::set);
		return ResponseEntity.created(new UriTemplate(GET_SINGLE_BUILDING_URI_TEMPLATE).expand(id.get()).normalize())
				.header("X-Created-Id", id.get())
				.build();
	}

	@GetMapping
	public List<BuildingJson> find(ListBuildingsRequestParams params)  {
		final JsonBuildingResponseModelListPresenter presenter = new JsonBuildingResponseModelListPresenter();
		listBuildings.execute(params.toRequest(), presenter);
		return presenter.getPresentedResult();
	}

	@GetMapping("/{buildingId}")
	public BuildingJson get(@PathVariable String buildingId)  {
		final JsonBuildingResponseModelPresenter presenter = new JsonBuildingResponseModelPresenter();
		getBuilding.execute(new GetBuildingRequest(buildingId), presenter);
		return presenter.getPresentedResult();
	}

	@PostMapping("{buildingId}/tenant")
	public ResponseEntity addTenant(@PathVariable("buildingId") String buildingId, @RequestBody AddTenantToBuildingJsonPayload payload) {
		final AtomicReference<String> id = new AtomicReference<>();
		addTenantToBuilding.execute(new AddTenantToBuildingRequest(buildingId, payload.getName()), id::set);
		return ResponseEntity.created(new UriTemplate(GET_BUILDING_TENANT_URI_TEMPLATE).expand(buildingId, id.get()).normalize()).build();
	}

	@DeleteMapping("{buildingId}/tenant/{tenantId}")
	public void evictTenant(@PathVariable("buildingId") String buildingId, @PathVariable("tenantId") String tenantId) {
		evictTenantFromBuilding.execute(new EvictTenantFromBuildingRequest(buildingId, tenantId));
	}
}
