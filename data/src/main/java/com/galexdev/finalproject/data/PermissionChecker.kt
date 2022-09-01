package com.galexdev.finalproject.data

/**
 * Created by GalexMP on 30/06/2022
 */
interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    fun check(permission: Permission): Boolean
}