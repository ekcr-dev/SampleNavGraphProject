package com.example.examplenavgraphbugproject

sealed class LocationItem {

    data class Location(
        val locationId: String,
        val name: String
    ) : LocationItem() {
        override val id = locationId
    }

    abstract val id: String
}