package be.insaneprogramming.cleanarch.jsonrpc;

import be.insaneprogramming.cleanarch.boundary.*;
import be.insaneprogramming.cleanarch.jsonrpc.viewmodel.BuildingJson;
import be.insaneprogramming.cleanarch.jsonrpc.viewmodel.TenantJson;
import be.insaneprogramming.cleanarch.requestmodel.*;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class BuildingServiceImpl implements BuildingService {
	private AddTenantToBuilding addTenantToBuilding;
	private EvictTenantFromBuilding evictTenantFromBuilding;
	private CreateBuilding createBuilding;
	private GetBuilding getBuilding;
	private ListBuildings listBuildings;

	public BuildingServiceImpl(AddTenantToBuilding addTenantToBuilding, EvictTenantFromBuilding evictTenantFromBuilding, CreateBuilding createBuilding, GetBuilding getBuilding, ListBuildings listBuildings) {
		this.addTenantToBuilding = addTenantToBuilding;
		this.evictTenantFromBuilding = evictTenantFromBuilding;
		this.createBuilding = createBuilding;
		this.getBuilding = getBuilding;
		this.listBuildings = listBuildings;
	}

	@Override
	public String addTenantToBuilding(String buildingId, String tenantName) {
		SimpleConsumer<String> consumer = new SimpleConsumer<>();
		addTenantToBuilding.execute(new AddTenantToBuildingRequest(buildingId, tenantName), consumer);
		return consumer.getContent();
	}

	@Override
	public String createBuilding(String name) {
		SimpleConsumer<String> consumer = new SimpleConsumer<>();
		createBuilding.execute(new CreateBuildingRequest(name), consumer);
		return consumer.getContent();
	}

	@Override
	public void evictTenantFromBuilding(String buildingId, String tenantId) {
		evictTenantFromBuilding.execute(new EvictTenantFromBuildingRequest(buildingId, tenantId));
	}

	@Override
	public BuildingJson getBuilding(String buildingId) {
		SimpleConsumer<BuildingResponseModel> consumer = new SimpleConsumer<>();
		getBuilding.execute(new GetBuildingRequest(buildingId), consumer);
		return present(consumer.getContent());
	}

	private BuildingJson present(BuildingResponseModel responseModel) {
		List<TenantJson> tenants = StreamSupport.stream(responseModel.getTenants().spliterator(), false).map(
				t -> new TenantJson(t.getId(), t.getName())
		).collect(Collectors.toList());
		return new BuildingJson(responseModel.getId(), responseModel.getName(), tenants);
	}

	@Override
	public List<BuildingJson> listBuildings(String nameStartsWith) {
		ListConsumer<BuildingResponseModel> consumer = new ListConsumer<>();
		listBuildings.execute(new ListBuildingsRequest.Builder().nameStartsWith(nameStartsWith).build(), consumer);
		return consumer.getContent().stream().map(this::present).collect(Collectors.toList());
	}

	private class SimpleConsumer<T> implements Consumer<T> {
		private T content;

		@Override
		public void accept(T s) {
			content = s;
		}

		public T getContent() {
			return content;
		}
	}

	private class ListConsumer<T> implements Consumer<T> {
		private List<T> content = new ArrayList<>();

		@Override
		public void accept(T s) {
			content.add(s);
		}

		public List<T> getContent() {
			return content;
		}
	}
}
