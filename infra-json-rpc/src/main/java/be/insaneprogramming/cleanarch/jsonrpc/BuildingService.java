package be.insaneprogramming.cleanarch.jsonrpc;

import be.insaneprogramming.cleanarch.jsonrpc.viewmodel.BuildingJson;

import java.util.List;

public interface BuildingService {
	String addTenantToBuilding(String buildingId, String tenantName);
	String createBuilding(String name);
	void evictTenantFromBuilding(String buildingId, String tenantId);
	BuildingJson getBuilding(String buildingId);
	List<BuildingJson> listBuildings(String nameStartsWith);
}
