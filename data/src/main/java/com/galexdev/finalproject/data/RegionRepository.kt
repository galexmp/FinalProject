package com.galexdev.finalproject.data


import com.galexdev.finalproject.data.datasource.LocationDataSource
import javax.inject.Inject

/**
 * Created by GalexMP on 30/06/2022
 */
class RegionRepository @Inject constructor(private val locationDataSource: LocationDataSource,
                                           private val permissionChecker: PermissionChecker) {
    companion object {
        const val DEFAULT_REGION = "US"
    }

    suspend fun findLastRegion(): String {
        return if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}