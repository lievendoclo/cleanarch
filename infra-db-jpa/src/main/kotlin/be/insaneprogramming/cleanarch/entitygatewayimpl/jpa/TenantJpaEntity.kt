package be.insaneprogramming.cleanarch.entitygatewayimpl.jpa

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class TenantJpaEntity (
    @Id
    val id: String = "",
    val name: String = ""
)
