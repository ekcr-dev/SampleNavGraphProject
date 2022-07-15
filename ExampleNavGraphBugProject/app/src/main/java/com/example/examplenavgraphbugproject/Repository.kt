package com.example.examplenavgraphbugproject

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class Repository @Inject constructor() {

    private var dr1 = Doctor("v01", "Dr. John Smith")
    private var dr2 = Doctor("v02", "Dr. Sarah Summers")
    private var dr3 = Doctor("v03", "Dr. Pat Kline")
    private var drList = listOf(dr1, dr2, dr3)
    private var loc1 = LocationItem.Location(
        "h01",
        "101 Main Street, Los Angeles, CA"
    )
    private var loc2 = LocationItem.Location(
        "h02",
        "234 Second Street, Chapel Hill, NC"
    )
    private var repository = listOf(loc1, loc2)
    private lateinit var selectedLocation: LocationItem.Location
    private lateinit var selectedDr: Doctor

    fun setLocationAndDr(locationStr: String?, drStr: String?): Single<String> {
        for (r in repository) {
            if (r.name == locationStr)
                selectedLocation = r
        }
        for (d in drList) {
            if (d.name == drStr)
                selectedDr = d
        }
        return Single.create { it.onSuccess("result") }
    }

    fun setDr(dr: Doctor?): Single<String> {
        if (dr != null)
            selectedDr = dr
        return Single.create { it.onSuccess("result") }
    }

    fun getDrs(): Single<List<Doctor>> {
        return Single.create { it.onSuccess(drList) }
    }

    fun getLocations(): Single<List<LocationItem.Location>> {
        return Single.create { it.onSuccess(repository) }
    }
}

