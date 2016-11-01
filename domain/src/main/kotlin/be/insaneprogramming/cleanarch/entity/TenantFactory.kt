package be.insaneprogramming.cleanarch.entity

import java.util.*

class TenantFactory {
    fun createTenant(id: TenantId, name: String): Tenant {
        return Tenant(id, name)
    }

    fun createTenant(name: String): Tenant {
        return createTenant(TenantId(UUID.randomUUID().toString()), name)
    }
}
