package be.insaneprogramming.cleanarch.interactor

import be.insaneprogramming.cleanarch.boundary.EvictTenantFromBuilding
import be.insaneprogramming.cleanarch.entity.Building
import be.insaneprogramming.cleanarch.entity.BuildingFactory
import be.insaneprogramming.cleanarch.entity.ImmutableBuildingId
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.requestmodel.ImmutableEvictTenantFromBuildingRequest
import spock.lang.Specification

class EvictTenantFromBuildingImplTest extends Specification {
	EvictTenantFromBuilding evictTenantFromBuilding;
	BuildingEntityGateway buildingEntityGateway;
	BuildingFactory buildingFactory;

	def setup() {
		buildingEntityGateway = Mock()
		buildingFactory = Mock()
		evictTenantFromBuilding = new EvictTenantFromBuildingImpl(buildingEntityGateway)
	}

	def "should evict tenant from building"() {
		given:
		def building = Mock(Building)
		buildingEntityGateway.findById(ImmutableBuildingId.of('buildingId')) >> building

		and:
		def request = ImmutableEvictTenantFromBuildingRequest.builder().buildingId('buildingId').tenantId('tenantId').build();

		when:
		evictTenantFromBuilding.execute(request)

		then:
		1 * building.evictTenant('tenantId')
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
