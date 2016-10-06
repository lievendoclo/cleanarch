package be.insaneprogramming.cleanarch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.insaneprogramming.cleanarch.jackson.MixInModule;

@Configuration
public class RestConfiguration {

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new MixInModule());
		return objectMapper;
	}
}
