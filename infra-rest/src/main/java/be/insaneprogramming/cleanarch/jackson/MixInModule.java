package be.insaneprogramming.cleanarch.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;

import be.insaneprogramming.cleanarch.requestmodel.AddTenantToBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.CreateBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.EvictTenantFromBuildingRequest;
import be.insaneprogramming.cleanarch.requestmodel.ListBuildingsRequest;

public class MixInModule extends SimpleModule {

	public MixInModule() {
		super("Clean architecture mixins");
	}

	@Override
	public void setupModule(SetupContext context) {
		context.setMixInAnnotations(AddTenantToBuildingRequest.class, AddTenantToBuildingRequestMixIn.class);
		context.setMixInAnnotations(EvictTenantFromBuildingRequest.class, EvictTenantFromBuildingRequestMixIn.class);
		context.setMixInAnnotations(ListBuildingsRequest.class, ListBuildingsRequestMixIn.class);
		context.setMixInAnnotations(CreateBuildingRequest.class, CreateBuildingRequestMixIn.class);
	}
}
