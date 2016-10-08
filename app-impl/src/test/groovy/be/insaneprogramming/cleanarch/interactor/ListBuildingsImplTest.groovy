package be.insaneprogramming.cleanarch.interactor

import be.insaneprogramming.cleanarch.boundary.ListBuildings
import be.insaneprogramming.cleanarch.entity.Building
import be.insaneprogramming.cleanarch.entity.ImmutableBuildingId
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.requestmodel.ImmutableListBuildingsRequest
import spock.lang.Specification

class ListBuildingsImplTest extends Specification {
	ListBuildings listBuildings;
	BuildingEntityGateway buildingEntityGateway;

	def setup() {
		buildingEntityGateway = Mock()
		listBuildings = new ListBuildingsImpl(buildingEntityGateway)
	}

	def "test execute"() {
		given:
		def buildings = [new Building(ImmutableBuildingId.of('one'), 'testOne'), new Building(ImmutableBuildingId.of('two'), 'testTwo')]
		buildingEntityGateway.findAll() >> buildings

		when:
		def response = listBuildings.execute(ImmutableListBuildingsRequest.builder().build())

		then:
		response.size() == 2
		response*.id == ["one", "two"]
		response*.name == ["testOne", "testTwo"]
	}

	def "request should not be null"() {
		given:
		def request = null

		when:
		listBuildings.execute(request)

		then:
		thrown(IllegalArgumentException)
	}
}
