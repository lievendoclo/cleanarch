package be.insaneprogramming.cleanarch.rest;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Named;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

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
import be.insaneprogramming.cleanarch.rest.requestparam.ListBuildingRequestParams;
import be.insaneprogramming.cleanarch.rest.viewmodel.BuildingJson;

@Path(BuildingResource.RESOURCE_URI_TEMPLATE)
@Named
public class BuildingResource {
	static final String RESOURCE_URI_TEMPLATE = "/building";
	private static final String GET_SINGLE_BUILDING_URI_TEMPLATE = RESOURCE_URI_TEMPLATE + "/{buildingId}";
	private static final String GET_BUILDING_TENANT_URI_TEMPLATE = GET_SINGLE_BUILDING_URI_TEMPLATE + "/tenant/{tenantId}";

	private AddTenantToBuilding addTenantToBuilding;
	private CreateBuilding createBuilding;
	private EvictTenantFromBuilding evictTenantFromBuilding;
	private ListBuildings listBuildings;
	private GetBuilding getBuilding;

	public BuildingResource(AddTenantToBuilding addTenantToBuilding, CreateBuilding createBuilding, EvictTenantFromBuilding evictTenantFromBuilding,
			ListBuildings listBuildings, GetBuilding getBuilding) {
		this.addTenantToBuilding = addTenantToBuilding;
		this.createBuilding = createBuilding;
		this.evictTenantFromBuilding = evictTenantFromBuilding;
		this.listBuildings = listBuildings;
		this.getBuilding = getBuilding;
	}

	@POST
	@Consumes("application/json")
	public Response create(CreateBuildingJsonPayload payload) {
		final AtomicReference<String> id = new AtomicReference<>();
		createBuilding.execute(new CreateBuildingRequest(payload.getName()), id::set);
		return Response.created(UriBuilder.fromPath(GET_SINGLE_BUILDING_URI_TEMPLATE).resolveTemplate("buildingId", id.get()).build())
				.header("X-Created-Id", id.get())
				.build();
	}

	@GET
	@Produces("application/json")
	public List<BuildingJson> list(@BeanParam ListBuildingRequestParams params)  {
		final JsonBuildingResponseModelListPresenter presenter = new JsonBuildingResponseModelListPresenter();
		listBuildings.execute(params.toRequest(), presenter);
		return presenter.getPresentedResult();
	}

	@GET
	@Produces("application/json")
	@Path("/{buildingId}")
	public BuildingJson get(@PathParam(("buildingId")) String buildingId)  {
		final JsonBuildingResponseModelPresenter presenter = new JsonBuildingResponseModelPresenter();
		getBuilding.execute(new GetBuildingRequest(buildingId), presenter);
		return presenter.getPresentedResult();
	}

	@POST
	@Consumes("application/json")
	@Path("{buildingId}/tenant")
	public Response addTenant(@PathParam("buildingId") String buildingId, AddTenantToBuildingJsonPayload payload) {
		final AtomicReference<String> id = new AtomicReference<>();
		addTenantToBuilding.execute(new AddTenantToBuildingRequest(buildingId, payload.getName()), id::set);
		return Response.created(UriBuilder.fromPath(GET_BUILDING_TENANT_URI_TEMPLATE).resolveTemplate("buildingId", buildingId).resolveTemplate("tenantId", id.get()).build()).build();
	}

	@DELETE
	@Path("{buildingId}/tenant/{tenantId}")
	public Response evictTenant(@PathParam("buildingId") String buildingId, @PathParam("tenantId") String tenantId) {
		evictTenantFromBuilding.execute(new EvictTenantFromBuildingRequest(buildingId, tenantId));
		return Response.ok().build();
	}
}
