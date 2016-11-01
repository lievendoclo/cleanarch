package be.insaneprogramming.cleanarch.entitygatewayimpl.jpa

import javax.persistence.*

@Entity
data class BuildingJpaEntity (
    @Id
    val id: String = "",
    val name: String = "",
    @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = javax.persistence.FetchType.EAGER)
    @JoinColumn(name = "building_id")
    val tenants: List<TenantJpaEntity> = mutableListOf()
)

