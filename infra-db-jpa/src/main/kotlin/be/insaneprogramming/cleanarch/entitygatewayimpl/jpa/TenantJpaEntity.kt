package be.insaneprogramming.cleanarch.entitygatewayimpl.jpa

import be.insaneprogramming.cleanarch.entity.TenantId
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class TenantJpaEntity {
    @Id
    var id: String = ""
    var name: String = ""

    internal constructor() {
    }

    constructor(id: TenantId, name: String) {
        this.id = id
        this.name = name
    }
}
