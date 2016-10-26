package be.insaneprogramming.cleanarch.interactor;

import java.util.ArrayList;
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
	private BuildingEntityGateway buildingEntityGateway;

	public ListBuildingsImpl(BuildingEntityGateway buildingEntityGateway) {
		this.buildingEntityGateway = buildingEntityGateway;
	}

	@Override
	public <T> CompletableFuture<T> execute(ListBuildingsRequest request, BuildingListPresenter<T> buildingListPresenter) {
		return CompletableFuture.supplyAsync(() -> {
					if (request == null) {
						throw new IllegalArgumentException("request should not be null");
					}
					List<BuildingResponseModel> response = new ArrayList<>();
					buildingEntityGateway.findAll().forEach(b -> {
							String id = b.getId().getValue();
							String name = b.getName();
							List<TenantResponseModel> tenantResponseModels = b.getTenants().stream().map(t ->
									new TenantResponseModel(t.getId().getValue(), t.getName())
							).collect(Collectors.toList());
								BuildingResponseModel buildingResponseModel = new BuildingResponseModel(id, name, tenantResponseModels);
								response.add(buildingResponseModel);
							}
					);
					return buildingListPresenter.present(response);
				}
		);

	}
}
