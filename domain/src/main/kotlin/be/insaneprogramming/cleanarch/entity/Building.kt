package be.insaneprogramming.cleanarch.entity

typealias BuildingId = String
typealias BuildingName = String
typealias TenantsInBuilding = MutableList<Tenant>

data class Building constructor(val id: BuildingId, val name: BuildingName, val tenants: TenantsInBuilding = mutableListOf()) {

    fun addTenant(tenant: Tenant) {
        this.tenants.add(tenant)
    }

    fun evictTenant(tenantId: TenantId) {
        this.tenants.removeIf { it -> it.id == tenantId }
    }
}
