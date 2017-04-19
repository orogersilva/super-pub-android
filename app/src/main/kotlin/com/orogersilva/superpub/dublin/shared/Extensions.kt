package com.orogersilva.superpub.dublin.shared

import android.app.Application
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.orogersilva.superpub.dublin.SuperPubApplication

/**
 * Created by orogersilva on 4/14/2017.
 */

// region ACTIVITY EXTENSION METHODS

fun AppCompatActivity.app(): SuperPubApplication = application as SuperPubApplication

fun <T> AppCompatActivity.intentFor(cls: Class<T>) = Intent(this, cls)

// endregion