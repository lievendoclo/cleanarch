package be.insaneprogramming.cleanarch.interactor

import be.insaneprogramming.cleanarch.boundary.AddTenantToBuilding
import be.insaneprogramming.cleanarch.entity.Building
import be.insaneprogramming.cleanarch.entity.Tenant
import be.insaneprogramming.cleanarch.entity.TenantFactory
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway
import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequestBuilder
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
		buildingEntityGateway.findById('buildingId') >> building
		def tenant = Mock(Tenant)
		tenantFactory.createTenant('tenantName') >> tenant

		and:
		def request = AddTenantToBuildingRequestBuilder.builder().buildingId('buildingId').name('tenantName').build()

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
