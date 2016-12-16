package be.insaneprogramming.cleanarch.entitygateway;

import java.util.List;

import be.insaneprogramming.cleanarch.entity.Building;

public interface BuildingEntityGateway {
	String save(Building building);
	List<Building> findAll();
	Building findById(String id);
}
