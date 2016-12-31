package be.insaneprogramming.cleanarch.entitygatewayimpl.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingJpaEntityRepository extends CrudRepository<BuildingJpaEntity, String> {
	List<BuildingJpaEntity> findByNameStartsWith(String name);
}