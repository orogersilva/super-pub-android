package com.orogersilva.superpub.dublin.data.local

import android.app.PendingIntent
import android.content.SharedPreferences
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import com.orogersilva.superpub.dublin.data.PreferencesDataSource
import io.reactivex.*
import javax.inject.Inject

/**
 * Created by orogersilva on 7/4/2017.
 */
@LoggedInScope
class UserPreferencesDataSource @Inject constructor(private val sharedPreferences: SharedPreferences,
                                                    private val sharedPreferencesEditor: SharedPreferences.Editor,
                                                    private val latPrefKey: String,
                                                    private val lngPrefKey: String,
                                                    private val locationSettingsFailureStatusCodePrefKey: String,
                                                    private val locationSettingsFailureStatusMessagePrefKey: String,
                                                    private val userLocationCallback: UserLocationCallback) : PreferencesDataSource {

    // region OVERRIDED METHODS

    override fun getLastLocation(): Flowable<Pair<Double, Double>> {

        if (sharedPreferences.contains(latPrefKey) && sharedPreferences.contains(lngPrefKey)) {

            val lat = java.lang.Double.longBitsToDouble(sharedPreferences.getLong(latPrefKey, 0L))
            val lng = java.lang.Double.longBitsToDouble(sharedPreferences.getLong(lngPrefKey, 0L))

            return Flowable.just(Pair(lat, lng))
        }

        return Flowable.create(object : FlowableOnSubscribe<Pair<Double, Double>> {

            override fun subscribe(emitter: FlowableEmitter<Pair<Double, Double>>) {

                userLocationCallback.setListener(emitter)

                sharedPreferences.registerOnSharedPreferenceChangeListener(userLocationCallback)
            }

        }, BackpressureStrategy.BUFFER).doOnCancel {

            sharedPreferences.unregisterOnSharedPreferenceChangeListener(userLocationCallback)

            userLocationCallback.removeListener()
        }
    }

    override fun saveLocation(lat: Double, lng: Double) {

        if (!isValidLocation(lat, lng)) throw IllegalArgumentException()

        sharedPreferencesEditor.putLong(latPrefKey, java.lang.Double.doubleToRawLongBits(lat))
        sharedPreferencesEditor.putLong(lngPrefKey, java.lang.Double.doubleToRawLongBits(lng))

        sharedPreferencesEditor.commit()
    }

    override fun setLocationSettingsFailureStatus(status: Status) {

        sharedPreferencesEditor.putInt(locationSettingsFailureStatusCodePrefKey, status.statusCode)
        sharedPreferencesEditor.putString(locationSettingsFailureStatusMessagePrefKey, status.statusMessage)

        userLocationCallback.setLocationFailureResolution(status.resolution)

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

    // region UTILITY CLASSES

    class UserLocationCallback @Inject constructor(private val latPrefKey: String,
                                                   private val lngPrefKey: String,
                                                   private val locationSettingsFailureStatusCodeKey: String,
                                                   private val locationSettingsFailureStatusMessageKey: String) : SharedPreferences.OnSharedPreferenceChangeListener {

        // region PROPERTIES

        private var userLocationEmitter: FlowableEmitter<Pair<Double, Double>>? = null
        private var locationFailureResolution: PendingIntent? = null

        // endregion

        // region METHODS

        fun setListener(emitter: FlowableEmitter<Pair<Double, Double>>?) {

            userLocationEmitter = emitter
        }

        fun setLocationFailureResolution(resolution: PendingIntent) {

            locationFailureResolution = resolution
        }

        fun removeListener() {

            userLocationEmitter = null
        }

        // endregion

        // region OVERRIDED METHODS

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

            when (key) {

                latPrefKey, lngPrefKey -> {

                    val lat = java.lang.Double.longBitsToDouble(sharedPreferences.getLong(latPrefKey, 0L))
                    val lng = java.lang.Double.longBitsToDouble(sharedPreferences.getLong(lngPrefKey, 0L))

                    userLocationEmitter?.onNext(Pair(lat, lng))
                    userLocationEmitter?.onComplete()
                }

                locationSettingsFailureStatusCodeKey, locationSettingsFailureStatusMessageKey -> {

                    val statusCode = sharedPreferences.getInt(locationSettingsFailureStatusCodeKey, 0)
                    val statusMessage = sharedPreferences.getString(locationSettingsFailureStatusMessageKey, null)

                    val locationSettingsResolvableApiException = ResolvableApiException(
                            Status(statusCode, statusMessage, locationFailureResolution))

                    userLocationEmitter?.onError(locationSettingsResolvableApiException)
                }
            }
        }

        // endregion
    }

    // endregion
}