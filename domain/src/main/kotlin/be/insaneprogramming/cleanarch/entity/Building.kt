package be.insaneprogramming.cleanarch.entity

data class Building constructor(val id: BuildingId, val name: String, val tenants: MutableList<Tenant> = mutableListOf()) {
    fun addTenant(tenant: Tenant) {
        this.tenants.add(tenant)
    }

    fun evictTenant(tenantId: String) {
        this.tenants.removeAll { it -> it.id == TenantId(tenantId) }
    }
}
