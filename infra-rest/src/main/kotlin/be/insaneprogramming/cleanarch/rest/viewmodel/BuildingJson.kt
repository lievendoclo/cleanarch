package be.insaneprogramming.cleanarch.rest.viewmodel

data class BuildingJson(val id: String, val name: String, val tenants: List<TenantJson>)
