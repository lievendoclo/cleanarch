package be.insaneprogramming.cleanarch.interactor

import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding
import be.insaneprogramming.cleanarch.entity.*
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest
import spock.lang.Specification

class EvictTenantFromBuildingImplTest extends Specification {
	EvictTenantFromBuilding evictTenantFromBuilding;
	BuildingEntityGateway buildingEntityGateway;
	BuildingFactory buildingFactory;

	def setup() {
		buildingEntityGateway = Mock()
		buildingFactory = new BuildingFactory()
		evictTenantFromBuilding = new EvictTenantFromBuildingImpl(buildingEntityGateway)
	}

	def "should evict tenant from building"() {
		given:
		def building = new Building(new BuildingId("buildingId"), "testBuilding", [new Tenant(new TenantId("tenantId"), "tenantName")])
		buildingEntityGateway.findById(new BuildingId('buildingId')) >> building

		and:
		def request = new EvictTenantFromBuildingRequest('buildingId','tenantId')

		when:
		evictTenantFromBuilding.execute(request)

		then:
		building.tenants.size() == 0
	}

	def "request should not be null"() {
		given:
		def request = null

		when:
		evictTenantFromBuilding.execute(request)

		then:
		thrown(IllegalArgumentException)
	}
}
