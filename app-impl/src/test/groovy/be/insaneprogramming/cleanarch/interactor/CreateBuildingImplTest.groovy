package be.insaneprogramming.cleanarch.interactor

import be.insaneprogramming.cleanarch.boundary.CreateBuilding
import be.insaneprogramming.cleanarch.entity.Building
import be.insaneprogramming.cleanarch.entity.BuildingFactory
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest
import spock.lang.Specification

class CreateBuildingImplTest extends Specification {
	CreateBuilding createBuilding
	BuildingEntityGateway buildingEntityGateway
	BuildingFactory buildingFactory

	def setup() {
		buildingEntityGateway = Mock()
		buildingFactory = new BuildingFactory()
		createBuilding = new CreateBuildingImpl(buildingFactory, buildingEntityGateway)
	}

	def "should create building"() {
		given:
		def request = new CreateBuildingRequest("testBuilding")
		buildingEntityGateway.save(_ as Building) >> "testId"

		when:
		def response = createBuilding.execute(request)

		then:
		response != null
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
