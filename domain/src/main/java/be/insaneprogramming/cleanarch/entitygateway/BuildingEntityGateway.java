package be.insaneprogramming.cleanarch.entitygateway;

import java.util.List;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingId;

public interface BuildingEntityGateway {
	String save(Building building);
	List<Building> findAll();
	Building findById(BuildingId id);
}
