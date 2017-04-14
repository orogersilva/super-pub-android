package com.orogersilva.superpub.dublin.shared

import android.content.Intent
import android.support.v7.app.AppCompatActivity

/**
 * Created by orogersilva on 4/14/2017.
 */

// region ACTIVITY EXTENSION METHODS

fun <T> AppCompatActivity.intentFor(cls: Class<T>) = Intent(this, cls)

// endregion