package be.insaneprogramming.cleanarch.entitygatewayimpl.morphia

import org.mongodb.morphia.annotations.Embedded
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id

@Entity
data class BuildingDocument(
        @Id val id: String,
        val name: String,
        @Embedded val tenants: List<TenantDocument> = mutableListOf()
)