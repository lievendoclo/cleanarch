package be.insaneprogramming.cleanarch;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Wiring {
	// Normally everything should get wired automatically
	// but when you use the Mongo infra layer, you may want
	// to define your own MongoClient bean to return something like
	// Fongo if you don't want to use a real MongoDB
}
