package be.insaneprogramming.cleanarch.presenter;

import java.util.List;
import java.util.stream.Collectors;

import be.insaneprogramming.cleanarch.boundary.BuildingListPresenter;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;
import be.insaneprogramming.cleanarch.rest.viewmodel.BuildingJson;
import be.insaneprogramming.cleanarch.rest.viewmodel.TenantJson;

public class JsonBuildingListPresenter implements BuildingListPresenter<List<BuildingJson>> {
	@Override
	public List<BuildingJson> present(List<BuildingResponseModel> buildingResponses) {
		return buildingResponses
				.stream()
				.map(it -> {
					List<TenantJson> tenants = it.getTenants().stream().map(t -> new TenantJson(t.getId(), t.getName())).collect(Collectors.toList());
					return new BuildingJson(it.getId(), it.getName(), tenants);
				})
				.collect(Collectors.toList());
	}
}
