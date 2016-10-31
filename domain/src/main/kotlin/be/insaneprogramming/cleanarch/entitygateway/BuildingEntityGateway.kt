package be.insaneprogramming.cleanarch.entitygateway

import be.insaneprogramming.cleanarch.entity.*

interface BuildingEntityGateway {
    fun save(building: Building): String
    fun findAll(): List<Building>
    fun findById(id: BuildingId): Building
}
