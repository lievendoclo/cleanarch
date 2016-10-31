package be.insaneprogramming.cleanarch.entity

import java.util.*

class BuildingFactory {

    fun createBuilding(id: BuildingId, name: String): Building {
        return Building(id, name)
    }

    fun createBuilding(name: String): Building {
        return createBuilding(UUID.randomUUID().toString(), name)
    }
}
