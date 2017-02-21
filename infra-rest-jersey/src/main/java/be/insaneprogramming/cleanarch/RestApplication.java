package be.insaneprogramming.cleanarch;

import javax.inject.Named;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import be.insaneprogramming.cleanarch.rest.BuildingResource;

@ApplicationPath("/")
@Named
public class RestApplication extends ResourceConfig {
	public RestApplication(BuildingResource buildingResource) {
		register(buildingResource);
	}
}
