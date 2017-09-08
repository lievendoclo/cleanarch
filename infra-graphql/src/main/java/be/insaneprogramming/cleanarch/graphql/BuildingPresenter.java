package be.insaneprogramming.cleanarch.graphql;

import be.insaneprogramming.cleanarch.graphql.model.BuildingJson;
import be.insaneprogramming.cleanarch.graphql.model.TenantJson;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BuildingPresenter {
	public static BuildingJson present(BuildingResponseModel responseModel) {
		List<TenantJson> tenants = StreamSupport.stream(responseModel.getTenants().spliterator(), false).map(
				t -> new TenantJson(t.getId(), t.getName())
		).collect(Collectors.toList());
		return new BuildingJson(responseModel.getId(), responseModel.getName(), tenants);
	}
}
