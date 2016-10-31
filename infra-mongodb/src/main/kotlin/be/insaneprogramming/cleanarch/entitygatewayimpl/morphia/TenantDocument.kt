package be.insaneprogramming.cleanarch.entitygatewayimpl.morphia

import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id

@Entity
class TenantDocument(
    @Id val id: String,
    val name: String
)

