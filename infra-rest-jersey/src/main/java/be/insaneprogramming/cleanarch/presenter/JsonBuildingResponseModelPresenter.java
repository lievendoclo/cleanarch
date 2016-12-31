package be.insaneprogramming.cleanarch.presenter;

import static java.util.stream.StreamSupport.stream;

import java.util.List;
import java.util.stream.Collectors;

import be.insaneprogramming.cleanarch.boundary.BuildingResponseModelPresenter;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;
import be.insaneprogramming.cleanarch.rest.viewmodel.BuildingJson;
import be.insaneprogramming.cleanarch.rest.viewmodel.TenantJson;

public class JsonBuildingResponseModelPresenter implements BuildingResponseModelPresenter<BuildingJson> {
	@Override
	public BuildingJson present(BuildingResponseModel buildingResponse) {
		List<TenantJson> tenants = stream(buildingResponse.getTenants().spliterator(), false).map(t -> new TenantJson(t.getId(), t.getName())).collect(Collectors.toList());
		return new BuildingJson(buildingResponse.getId(), buildingResponse.getName(), tenants);
	}
}
