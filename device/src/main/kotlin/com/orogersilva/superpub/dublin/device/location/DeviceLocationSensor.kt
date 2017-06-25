package com.orogersilva.superpub.dublin.device.location

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import com.orogersilva.superpub.dublin.domain.manager.LocationSensor
import io.reactivex.*
import javax.inject.Inject

/**
 * Created by orogersilva on 5/26/2017.
 */
@LoggedInScope
class DeviceLocationSensor @Inject constructor(private val googleApiClient: GoogleApiClient,
                                               private val deviceLocationConnectionSubscriber: DeviceLocationConnectionSubscriber,
                                               private val deviceLocationListener: DeviceLocationListener)
    : LocationSensor<Pair<Double, Double>> {

    // region INITIALIZE BLOCK

    init {deviceLocationListener

        googleApiClient.registerConnectionCallbacks(deviceLocationConnectionSubscriber)
        googleApiClient.registerConnectionFailedListener(deviceLocationConnectionSubscriber)
    }

    // endregion

    // region OVERRIDED METHODS

    override fun getLastLocation(): Flowable<Pair<Double, Double>> =
            Flowable.create(object : FlowableOnSubscribe<Pair<Double, Double>> {

                override fun subscribe(emitter: FlowableEmitter<Pair<Double, Double>>?) {

                    deviceLocationListener.setListener(emitter)

                    deviceLocationConnectionSubscriber.setListener(object : DeviceLocationConnectionSubscriber.OnConnectionListener {

                        @SuppressLint("MissingPermission")
                        override fun onConnected(bundle: Bundle?) {

                            /*val LOCATION_REQUEST_INTERVAL = 30000L
                            val LOCATION_REQUEST_FASTEST_INTERVAL = 10000L
                            val LOCATION_REQUEST_PRIORITY = LocationRequest.PRIORITY_HIGH_ACCURACY*/

                            val lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)

                            lastLocation.let {
                                emitter?.onNext(Pair<Double, Double>(it.latitude, it.longitude))
                                emitter?.onComplete()
                            }

                            /*val locationRequest = createLocationRequest(LOCATION_REQUEST_INTERVAL,
                                    LOCATION_REQUEST_FASTEST_INTERVAL, LOCATION_REQUEST_PRIORITY)*/

                            // startLocationUpdates(locationRequest)
                        }

                        override fun onConnectionFailed(connectionResult: ConnectionResult) {

                            emitter?.onError(Exception(connectionResult.errorMessage))
                        }
                    })

                    googleApiClient.connect()
                }

            }, BackpressureStrategy.BUFFER).doOnCancel {

                // stopLocationUpdates()

                googleApiClient.disconnect()
            }

    // endregion

    // region UTILITY METHODS

    private fun createLocationRequest(interval: Long, fastestInterval: Long, priority: Int): LocationRequest {

        val locationRequest = LocationRequest()

        locationRequest.interval = interval
        locationRequest.fastestInterval = fastestInterval
        locationRequest.priority = priority

        return locationRequest
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(locationRequest: LocationRequest) {

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, deviceLocationListener)
    }

    internal fun stopLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, deviceLocationListener)
    }

    // endregion

    // region INNER CLASSES

    class DeviceLocationConnectionSubscriber : GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener {

        // region PROPERTIES

        private lateinit var onConnectionListener: OnConnectionListener

        // endregion

        // region OVERRIDED METHODS

        override fun onConnected(bundle: Bundle?) {

            onConnectionListener.onConnected(bundle)
        }

        override fun onConnectionSuspended(i: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onConnectionFailed(connectionResult: ConnectionResult) {

            onConnectionListener.onConnectionFailed(connectionResult)
        }

        // endregion

        // region METHODS

        fun setListener(onConnectionListener: OnConnectionListener) {

            this.onConnectionListener = onConnectionListener
        }

        // endregion

        // region INTERFACES

        interface OnConnectionListener {

            // region METHODS

            fun onConnected(bundle: Bundle?)
            fun onConnectionFailed(connectionResult: ConnectionResult)

            // endregion
        }

        // endregion
    }

    class DeviceLocationListener : LocationListener {

        // region PROPERTIES

        private var locationEmitter: FlowableEmitter<Pair<Double, Double>>? = null

        // endregion

        // region OVERRIDED METHODS

        override fun onLocationChanged(newLocation: Location?) {

            if (newLocation != null) {
                locationEmitter?.onNext(Pair(newLocation.latitude, newLocation.longitude))
            }
        }

        // endregion

        // region METHODS

        fun setListener(locationEmitter: FlowableEmitter<Pair<Double, Double>>?) {

            this.locationEmitter = locationEmitter
        }

        // endregion
    }

    // endregion
}