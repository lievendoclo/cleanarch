package be.insaneprogramming.cleanarch;

import org.junit.Before;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("mongodb")
public class MongoDBBuildingControllerIntegrationTest extends AbstractBuildingControllerIntegrationTest {
	@Autowired
	private Datastore datastore;

	// MongoDB is not transactional, so we need to drop the collections. Luckily this is a cheap operation.
	@Before
	public void resetMongoDBCollections() {
		datastore.getDB().getCollectionNames().forEach(it -> datastore.getDB().getCollection(it).drop());
	}

}
