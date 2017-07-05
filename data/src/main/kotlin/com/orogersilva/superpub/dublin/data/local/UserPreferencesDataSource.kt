package com.orogersilva.superpub.dublin.data.local

import android.content.SharedPreferences
import android.util.Log
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import com.orogersilva.superpub.dublin.domain.local.PreferencesDataSource
import javax.inject.Inject

/**
 * Created by orogersilva on 7/4/2017.
 */
@LoggedInScope
class UserPreferencesDataSource @Inject constructor(private val sharedPreferences: SharedPreferences,
                                                    private val sharedPreferencesEditor: SharedPreferences.Editor,
                                                    private val latPrefKey: String,
                                                    private val lngPrefKey: String) : PreferencesDataSource {

    // region OVERRIDED METHODS

    override fun getLastLocation(): Pair<Double, Double> {

        val lat = java.lang.Double.longBitsToDouble(sharedPreferences.getLong(latPrefKey, 0L))
        val lng = java.lang.Double.longBitsToDouble(sharedPreferences.getLong(lngPrefKey, 0L))

        Log.d("UsPreferencesDataSource", "getLastLocation -> Lat: $lat; Lng: $lng")

        return Pair(lat, lng)
    }

    override fun saveLocation(lat: Double, lng: Double) {

        Log.d("UsPreferencesDataSource", "saveLocation -> Lat: $lat; Lng: $lng")

        if (!isValidLocation(lat, lng)) throw IllegalArgumentException()

        sharedPreferencesEditor.putLong(latPrefKey, java.lang.Double.doubleToRawLongBits(lat))
        sharedPreferencesEditor.putLong(lngPrefKey, java.lang.Double.doubleToRawLongBits(lng))

        sharedPreferencesEditor.commit()
    }

    override fun clear() {

        sharedPreferencesEditor.clear()
        sharedPreferencesEditor.commit()
    }

    // endregion

    // region UTILITY METHODS

    private fun isValidLocation(lat: Double, lng: Double): Boolean {

        val VALID_MIN_LAT = -90.0
        val VALID_MAX_LAT = 90.0
        val VALID_MIN_LNG = -180.0
        val VALID_MAX_LNG = 180.0

        return lat in VALID_MIN_LAT..VALID_MAX_LAT && lng in VALID_MIN_LNG..VALID_MAX_LNG
    }

    // endregion
}