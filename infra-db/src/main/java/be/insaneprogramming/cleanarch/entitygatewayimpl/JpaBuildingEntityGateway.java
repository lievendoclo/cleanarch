package be.insaneprogramming.cleanarch.entitygatewayimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingFactory;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntity;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntityRepository;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.TenantJpaEntity;

@Named
public class JpaBuildingEntityGateway implements BuildingEntityGateway {
	private BuildingJpaEntityRepository buildingJpaEntityRepository;
	private BuildingFactory buildingFactory;

	@Inject
	public JpaBuildingEntityGateway(BuildingJpaEntityRepository buildingJpaEntityRepository, BuildingFactory buildingFactory) {
		this.buildingJpaEntityRepository = buildingJpaEntityRepository;
		this.buildingFactory = buildingFactory;
	}

	@Override
	public String save(Building building) {
		return buildingJpaEntityRepository.save(fromDomain(building)).getId();
	}

	@Override
	public List<Building> findAll() {
		List<Building> buildings = new ArrayList<>();
		buildingJpaEntityRepository.findAll().forEach(it -> buildings.add(toDomain(it)));
		return buildings;
	}

	@Override
	public Building findById(String id) {
		return toDomain(buildingJpaEntityRepository.findOne(id));
	}

	private BuildingJpaEntity fromDomain(Building building) {
		BuildingJpaEntity jpaEntity = new BuildingJpaEntity();
		jpaEntity.setId(building.id);
		jpaEntity.setName(building.name);
		jpaEntity.setTenants(building.tenants.stream().map(this::fromDomain).collect(Collectors.toList()));
		return jpaEntity;
	}

	private Building toDomain(BuildingJpaEntity entity) {
		Building building = buildingFactory.createBuilding(entity.getId(), entity.getName());
		building.tenants = entity.getTenants().stream().map(this::toDomain).collect(Collectors.toList());
		return building;
	}

	private TenantJpaEntity fromDomain(Tenant tenant) {
		TenantJpaEntity entity = new TenantJpaEntity();
		entity.setId(tenant.getId());
		entity.setName(tenant.getName());
		return entity;
	}

	private Tenant toDomain(TenantJpaEntity entity) {
		return new Tenant(entity.getId(), entity.getName());
	}
}
