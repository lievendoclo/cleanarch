package be.insaneprogramming.cleanarch.entitygatewayimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingFactory;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entity.TenantFactory;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntity;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntityRepository;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.TenantJpaEntity;

public class JpaBuildingEntityGateway implements BuildingEntityGateway {
	private final BuildingJpaEntityRepository buildingJpaEntityRepository;
	private final BuildingFactory buildingFactory;
	private final TenantFactory tenantFactory;

	public JpaBuildingEntityGateway(BuildingJpaEntityRepository buildingJpaEntityRepository, BuildingFactory buildingFactory, TenantFactory tenantFactory) {
		this.buildingJpaEntityRepository = buildingJpaEntityRepository;
		this.buildingFactory = buildingFactory;
		this.tenantFactory = tenantFactory;
	}

	public String save(Building building) {
		return buildingJpaEntityRepository.save(fromDomain(building)).getId();
	}

	public List<Building> findAll() {
		List<Building> buildings = new ArrayList<>();
		buildingJpaEntityRepository.findAll().forEach(it -> buildings.add(toDomain(it)));
		return buildings;
	}

	public Building findById(String buildingId) {
		return toDomain(buildingJpaEntityRepository.findOne(buildingId));
	}

	private BuildingJpaEntity fromDomain(Building building) {
		return new BuildingJpaEntity(
				building.getId(),
				building.getName(),
				building.getTenants().stream().map(this::fromDomain).collect(Collectors.toList())
        );
	}

	private Building toDomain(BuildingJpaEntity entity) {
		Building building = buildingFactory.createBuilding(entity.getId(), entity.getName());
		entity.getTenants().stream().map(this::toDomain).forEach(building::addTenant);
		return building;
	}

	private TenantJpaEntity fromDomain(Tenant tenant) {
		return new TenantJpaEntity(
				tenant.getId(),
				tenant.getName()
		);
	}

	private Tenant toDomain(TenantJpaEntity entity) {
		return tenantFactory.createTenant(entity.getId(), entity.getName());
	}
}
