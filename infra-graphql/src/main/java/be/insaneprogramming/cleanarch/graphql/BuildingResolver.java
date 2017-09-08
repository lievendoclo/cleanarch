package be.insaneprogramming.cleanarch.graphql;

import be.insaneprogramming.cleanarch.boundary.GetBuilding;
import be.insaneprogramming.cleanarch.graphql.model.BuildingJson;
import be.insaneprogramming.cleanarch.requestmodel.GetBuildingRequest;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;
import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

@Component
public class BuildingResolver implements GraphQLResolver<BuildingJson> {
	private GetBuilding getBuilding;

	public BuildingResolver(GetBuilding getBuilding) {
		this.getBuilding = getBuilding;
	}
	public BuildingJson getBuilding(String buildingId) {
		SimpleConsumer<BuildingResponseModel> consumer = new SimpleConsumer<>();
		getBuilding.execute(new GetBuildingRequest(buildingId), consumer);
		return BuildingPresenter.present(consumer.getContent());
	}
}
