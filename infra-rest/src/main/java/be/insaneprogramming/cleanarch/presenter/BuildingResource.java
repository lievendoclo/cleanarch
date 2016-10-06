package be.insaneprogramming.cleanarch.presenter;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding;
import be.insaneprogramming.cleanarch.boundary.CreateBuilding;
import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding;
import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;
import be.insaneprogramming.cleanarch.responsemodel.CreateBuildingResponse;
import be.insaneprogramming.cleanarch.responsemodel.ListBuildingsResponse;

@RestController
@RequestMapping("/building")
public class BuildingResource {
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
	public CreateBuildingResponse create(@RequestBody CreateBuildingRequest request) {
		return createBuilding.execute(request);
	}

	@GetMapping
	public ListBuildingsResponse list(@RequestBody ListBuildingsRequest request) {
		return listBuildings.execute(request);
	}

	@GetMapping("{buildingId}/tenant")
	public void addTenant(@PathVariable("buildingId") String buildingId, @RequestBody AddTenantToBuildingRequest request) {
		addTenantToBuilding.execute(request);
	}

	@DeleteMapping("{buildingId}/tenant/{tenantId}")
	public void evictTenant(@PathVariable("buildingId") String buildingId, @PathVariable("tenantId") String tenantId) {
		EvictTenantFromBuildingRequest request = new EvictTenantFromBuildingRequest(buildingId, tenantId);
		evictTenantFromBuilding.execute(request);
	}
}
