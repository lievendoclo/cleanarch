package be.insaneprogramming.cleanarch.entitygatewayimpl.jpa

import be.insaneprogramming.cleanarch.entity.BuildingId
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany

@Entity
class BuildingJpaEntity {
    @Id
    var id: String = ""
    var name: String = ""
    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "building_id")
    var tenants: List<TenantJpaEntity> = mutableListOf()

    internal constructor() {
    }

    constructor(id: BuildingId, name: String, tenants: List<TenantJpaEntity>) {
        this.id = id
        this.name = name
        this.tenants = tenants
    }
}
