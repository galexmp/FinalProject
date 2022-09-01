package com.galexdev.finalproject.data

import com.galexdev.finalproject.data.PermissionChecker.Permission.COARSE_LOCATION
import com.galexdev.finalproject.data.datasource.LocationDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class RegionRepositoryTest{

    @Test
    fun `Return default region when coarse permission not granted`(): Unit = runBlocking{
        val regionRepository = buildRegionRepository(
            permissionChecker = mock{ on { check(COARSE_LOCATION)} doReturn false }
        )

        val region = regionRepository.findLastRegion()

        assertEquals(RegionRepository.DEFAULT_REGION, region)
    }

    @Test
    fun  `Returns region from location data source when permission granted`(): Unit = runBlocking {
        val regionRepository = buildRegionRepository(
            locationDataSource = mock{ onBlocking { findLastRegion()} doReturn "ES" },
            permissionChecker = mock{on { check(COARSE_LOCATION)} doReturn true }
        )

        val region = regionRepository.findLastRegion()

        assertEquals("ES", region)
    }
}

private fun buildRegionRepository(
    locationDataSource: LocationDataSource = mock(),
    permissionChecker: PermissionChecker = mock()
) = RegionRepository(locationDataSource, permissionChecker)