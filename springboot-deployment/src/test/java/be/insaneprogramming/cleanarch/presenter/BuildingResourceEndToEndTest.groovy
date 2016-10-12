package be.insaneprogramming.cleanarch.presenter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BuildingResourceEndToEndTest extends Specification {

	@Autowired
	private TestRestTemplate restTemplate;

	private MockMvc mockMvc;

	def "creating a building and get it"() {
		given:
		def buildingBody = [name: "test"]
		def tenantBody = [name: "test"]
		def buildingLocation
		def tenantLocation
		def entity

		when:
		entity = restTemplate.postForEntity("/building", buildingBody, Void.class)
		buildingLocation = entity.headers.get("Location")[0]

		then:
		entity != null
		entity.statusCode == HttpStatus.CREATED

		when:
		entity = restTemplate.getForEntity("/building", List.class)

		then:
		entity != null
		entity.statusCode == HttpStatus.OK
		entity.body.size() == 1
		entity.body[0].name == "test"

		when:
		entity = restTemplate.postForEntity(buildingLocation + "/tenant", tenantBody, Void.class)

		then:
		entity != null
		entity.statusCode == HttpStatus.CREATED

		when:
		entity = restTemplate.getForEntity("/building", List.class)

		then:
		entity != null
		entity.statusCode == HttpStatus.OK
		entity.body.size() == 1
		entity.body[0].tenants.size() == 1
		entity.body[0].tenants[0].name == "test"
	}
}
