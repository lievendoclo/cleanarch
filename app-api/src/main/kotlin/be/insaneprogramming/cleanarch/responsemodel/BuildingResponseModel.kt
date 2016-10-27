package be.insaneprogramming.cleanarch.responsemodel

data class BuildingResponseModel(val id: String, val name: String, val tenants: List<TenantResponseModel>)
