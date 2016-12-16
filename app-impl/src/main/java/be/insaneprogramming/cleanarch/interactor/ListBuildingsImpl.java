package be.insaneprogramming.cleanarch.interactor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import be.insaneprogramming.cleanarch.boundary.BuildingListPresenter;
import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;
import be.insaneprogramming.cleanarch.responsemodel.TenantResponseModel;

public class ListBuildingsImpl implements ListBuildings {
	private final BuildingEntityGateway buildingEntityGateway;

	public ListBuildingsImpl(BuildingEntityGateway buildingEntityGateway) {
		this.buildingEntityGateway = buildingEntityGateway;
	}

	@Override
	public <T> CompletableFuture<T> execute(ListBuildingsRequest request, BuildingListPresenter<T> buildingListPresenter) {
		if(request == null) {
			throw new IllegalArgumentException("request should not be null");
		}
		if(buildingListPresenter == null) {
			throw new IllegalArgumentException("buildingListPresenter should not be null");
		}
		return CompletableFuture.supplyAsync(() ->
				buildingListPresenter.present(buildingEntityGateway.findAll().stream().map(b -> {
							List<TenantResponseModel> tenantResponseModels = b.getTenants().stream().map(it -> new TenantResponseModel(it.getId(), it.getName())).collect(Collectors.toList());
							return new BuildingResponseModel(b.getId(), b.getName(), tenantResponseModels);
						}).collect(Collectors.toList())
				)
		);
	}
}
