package be.insaneprogramming.cleanarch.graphql;

import be.insaneprogramming.cleanarch.boundary.ListBuildings;
import be.insaneprogramming.cleanarch.graphql.model.BuildingJson;
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;
import be.insaneprogramming.cleanarch.responsemodel.BuildingResponseModel;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Query implements GraphQLQueryResolver {
    private ListBuildings listBuildings;

    public Query(ListBuildings listBuildings) {
        this.listBuildings = listBuildings;
    }

    public List<BuildingJson> list(String nameStartsWith) {

        ListConsumer<BuildingResponseModel> consumer = new ListConsumer<>();
        listBuildings.execute(new ListBuildingsRequest.Builder().nameStartsWith(nameStartsWith).build(), consumer);
        return consumer.getContent().stream().map(BuildingPresenter::present).collect(Collectors.toList());
    }
}