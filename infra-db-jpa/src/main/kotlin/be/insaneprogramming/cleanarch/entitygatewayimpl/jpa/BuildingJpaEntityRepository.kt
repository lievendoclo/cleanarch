package be.insaneprogramming.cleanarch.entitygatewayimpl.jpa

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BuildingJpaEntityRepository : CrudRepository<BuildingJpaEntity, String>
