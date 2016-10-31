package be.insaneprogramming.cleanarch.entity

typealias BuildingId = String

data class Building constructor(val id: BuildingId, val name: String, val tenants: MutableList<Tenant> = mutableListOf()) {
    fun addTenant(tenant: Tenant) {
        this.tenants.add(tenant)
    }

    fun evictTenant(tenantId: String) {
        this.tenants.removeIf { it -> it.id == tenantId }
    }
}
