package be.insaneprogramming.cleanarch.presenter;

import static java.util.stream.StreamSupport.stream;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;
import be.insaneprogramming.cleanarch.rest.viewmodel.BuildingJson;
import be.insaneprogramming.cleanarch.rest.viewmodel.TenantJson;

public class JsonBuildingResponseModelPresenter implements Consumer<BuildingResponseModel> {
	private BuildingJson buildingJson;

	@Override
	public void accept(BuildingResponseModel buildingResponse) {
		List<TenantJson> tenants = stream(buildingResponse.getTenants().spliterator(), false).map(t -> new TenantJson(t.getId(), t.getName())).collect(Collectors.toList());
		buildingJson = new BuildingJson(buildingResponse.getId(), buildingResponse.getName(), tenants);
	}

	public BuildingJson getPresentedResult() {
		return buildingJson;
	}
}
