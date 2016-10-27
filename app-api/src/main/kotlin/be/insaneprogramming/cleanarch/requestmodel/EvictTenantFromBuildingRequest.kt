package be.insaneprogramming.cleanarch.requestmodel

data class EvictTenantFromBuildingRequest(val buildingId: String, val tenantId: String)