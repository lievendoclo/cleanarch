package be.insaneprogramming.cleanarch.entitygatewayimpl.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingJpaEntityRepository extends CrudRepository<BuildingJpaEntity, String> {

}