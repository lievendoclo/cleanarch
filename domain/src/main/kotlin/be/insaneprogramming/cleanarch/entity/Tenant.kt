package be.insaneprogramming.cleanarch.entity

typealias TenantId = String

data class Tenant(val id: TenantId, val name: String) {
    typealias TenantId = String
}
