package be.insaneprogramming.cleanarch.interactor

import be.insaneprogramming.cleanarch.boundary.ListBuildings
import be.insaneprogramming.cleanarch.entity.Building
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest
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
		def buildings = [new Building('one', 'testOne'), new Building('two', 'testTwo')]
		buildingEntityGateway.findAll() >> buildings

		and:
		def request = new ListBuildingsRequest()

		when:
		def response = listBuildings.execute(request)

		then:
		response.items.size() == 2
		response.items*.id == ["one", "two"]
		response.items*.name == ["testOne", "testTwo"]
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
