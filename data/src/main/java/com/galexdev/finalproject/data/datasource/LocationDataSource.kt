package com.galexdev.finalproject.data.datasource

/**
 * Created by GalexMP on 30/06/2022
 */
interface LocationDataSource {
    suspend fun findLastRegion(): String?
}
