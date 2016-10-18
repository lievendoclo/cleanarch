package be.insaneprogramming.cleanarch.presenter;

import java.util.List;
import java.util.stream.Collectors;

import be.insaneprogramming.cleanarch.boundary.BuildingListPresenter;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;
import be.insaneprogramming.cleanarch.rest.viewmodel.BuildingJson;
import be.insaneprogramming.cleanarch.rest.viewmodel.ImmutableBuildingJson;
import be.insaneprogramming.cleanarch.rest.viewmodel.ImmutableTenantJson;

public class JsonBuildingListPresenter implements BuildingListPresenter<List<BuildingJson>> {
	@Override
	public List<BuildingJson> present(List<BuildingResponseModel> buildingResponses) {
		return buildingResponses
				.stream()
				.map(it -> {
					ImmutableBuildingJson.Builder builder = ImmutableBuildingJson.builder()
							.id(it.getId())
							.name(it.getName());
					it.getTenants().forEach(t ->
							builder.addTenants(ImmutableTenantJson.builder().id(t.getId()).name(t.getName()).build())
					);
					return builder.build();
				})
				.collect(Collectors.toList());
	}
}
