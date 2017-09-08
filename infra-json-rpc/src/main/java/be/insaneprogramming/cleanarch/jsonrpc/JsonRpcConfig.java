package be.insaneprogramming.cleanarch.jsonrpc;

import com.googlecode.jsonrpc4j.spring.JsonServiceExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class JsonRpcConfig {
	@Bean(name = "/rpc/building")
	public JsonServiceExporter buildingServiceExporter(BuildingService buildingService) {
		JsonServiceExporter exporter = new JsonServiceExporter();
		exporter.setService(buildingService);
		exporter.setServiceInterface(BuildingService.class);
		return exporter;
	}
}
