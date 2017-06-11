package com.orogersilva.superpub.dublin.shared

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.orogersilva.superpub.dublin.SuperPubApplication
import java.util.*

/**
 * Created by orogersilva on 4/14/2017.
 */

// region ACTIVITY EXTENSION METHODS

fun AppCompatActivity.app(): SuperPubApplication = application as SuperPubApplication

fun AppCompatActivity.hasPermission(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun AppCompatActivity.permissionsHasBeenGranted(grantResults: IntArray): Boolean {

    grantResults.forEach { if (it != PackageManager.PERMISSION_GRANTED) return false }

    return true
}

fun <T> AppCompatActivity.intentFor(cls: Class<T>) = Intent(this, cls)

// endregion