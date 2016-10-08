package be.insaneprogramming.cleanarch.interactor

import be.insaneprogramming.cleanarch.boundary.CreateBuilding
import be.insaneprogramming.cleanarch.entity.Building
import be.insaneprogramming.cleanarch.entity.BuildingFactory
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.requestmodel.ImmutableCreateBuildingRequest
import spock.lang.Specification

class CreateBuildingImplTest extends Specification {
	CreateBuilding createBuilding;
	BuildingEntityGateway buildingEntityGateway;
	BuildingFactory buildingFactory;

	def setup() {
		buildingEntityGateway = Mock()
		buildingFactory = Mock()
		createBuilding = new CreateBuildingImpl(buildingEntityGateway, buildingFactory)
	}

	def "should create building"() {
		given:
		def createdBuilding = Mock(Building)
		buildingFactory.createBuilding('testBuilding') >> createdBuilding
		buildingEntityGateway.save(createdBuilding) >> 'testId'

		and:
		def request = ImmutableCreateBuildingRequest.builder().name('testBuilding').build()

		when:
		def response = createBuilding.execute(request)

		then:
		response == 'testId'
	}

	def "request should not be null"() {
		given:
		def request = null

		when:
		createBuilding.execute(request)

		then:
		thrown(IllegalArgumentException)
	}
}
