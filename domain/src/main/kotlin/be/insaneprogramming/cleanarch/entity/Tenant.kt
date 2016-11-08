package be.insaneprogramming.cleanarch.entity

typealias TenantId = String
typealias TenantName = String

data class Tenant(val id: TenantId, val name: TenantName) {
}
