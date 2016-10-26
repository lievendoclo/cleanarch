package be.insaneprogramming.cleanarch.entitygatewayimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import be.insaneprogramming.cleanarch.entity.Building;
import be.insaneprogramming.cleanarch.entity.BuildingFactory;
import be.insaneprogramming.cleanarch.entity.BuildingId;
import be.insaneprogramming.cleanarch.entity.Tenant;
import be.insaneprogramming.cleanarch.entity.TenantFactory;
import be.insaneprogramming.cleanarch.entity.TenantId;
import be.insaneprogramming.cleanarch.entitygateway.BuildingEntityGateway;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntity;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.BuildingJpaEntityRepository;
import be.insaneprogramming.cleanarch.entitygatewayimpl.jpa.TenantJpaEntity;

public class JpaBuildingEntityGateway implements BuildingEntityGateway {
	private BuildingJpaEntityRepository buildingJpaEntityRepository;
	private BuildingFactory buildingFactory;
	private TenantFactory tenantFactory;

	public JpaBuildingEntityGateway(BuildingJpaEntityRepository buildingJpaEntityRepository, BuildingFactory buildingFactory, TenantFactory tenantFactory) {
		this.buildingJpaEntityRepository = buildingJpaEntityRepository;
		this.buildingFactory = buildingFactory;
		this.tenantFactory = tenantFactory;
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
	public Building findById(BuildingId id) {
		return toDomain(buildingJpaEntityRepository.findOne(id.getValue()));
	}

	private BuildingJpaEntity fromDomain(Building building) {
		return new BuildingJpaEntity(
				building.getId(),
				building.getName(),
				building.getTenants().stream().map(this::fromDomain).collect(Collectors.toList())
		);
	}

	private Building toDomain(BuildingJpaEntity entity) {
		Building building = buildingFactory.createBuilding(BuildingId.of(entity.getId()), entity.getName());
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
		return tenantFactory.createTenant(TenantId.of(entity.getId()), entity.getName());
	}
}
