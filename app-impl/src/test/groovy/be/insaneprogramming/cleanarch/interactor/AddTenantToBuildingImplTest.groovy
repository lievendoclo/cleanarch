package be.insaneprogramming.cleanarch.interactor

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding
import be.insaneprogramming.cleanarch.entity.*
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest
import spock.lang.Specification

class AddTenantToBuildingImplTest extends Specification {
	AddTenantToBuilding addTenantToBuilding;
	BuildingEntityGateway buildingEntityGateway;
	TenantFactory tenantFactory;

	def setup() {
		buildingEntityGateway = Mock()
		tenantFactory = Mock()
		addTenantToBuilding = new AddTenantToBuildingImpl(buildingEntityGateway, tenantFactory)
	}

	def "should add tenant to building"() {
		given:
		def building = Mock(Building)
		buildingEntityGateway.findById(BuildingId.of('buildingId')) >> building
		def tenant = Mock(Tenant)
		tenantFactory.createTenant('tenantName') >> tenant
		tenant.getId() >> TenantId.of("tenantId")

		and:
		def request = new AddTenantToBuildingRequest('buildingId','tenantName')

		when:
		addTenantToBuilding.execute(request)

		then:
		1 * building.addTenant(tenant)
	}

	def "request should not be null"() {
		given:
		def request = null

		when:
		addTenantToBuilding.execute(request)

		then:
		thrown(IllegalArgumentException)
	}
}
