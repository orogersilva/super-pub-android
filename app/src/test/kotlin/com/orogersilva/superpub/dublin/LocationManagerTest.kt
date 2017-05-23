package com.orogersilva.superpub.dublin

import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.api.GoogleApiClient
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Created by orogersilva on 5/23/2017.
 */
class LocationManagerTest {

    // region PROPERTIES

    private lateinit var sourceActivityMock: AppCompatActivity
    private lateinit var googleApiClientMock: GoogleApiClient
    private lateinit var locationCallbackMock: LocationCallback

    private lateinit var locationManager: LocationManager

    // endregion

    // region SETUP METHODS

    @Before fun setup() {

        sourceActivityMock = mock()
        googleApiClientMock = mock()
        locationCallbackMock = mock()

        locationManager = spy(LocationManager(sourceActivityMock, googleApiClientMock, locationCallbackMock))
    }

    // endregion

    // region TEST METHODS

    @Test fun connect_successfully() {

        // ACT

        locationManager.connect()

        // ASSERT

        verify(googleApiClientMock).connect()
    }

    @Test fun disconnect_successfully() {

        // ACT

        locationManager.disconnect()

        // ASSERT

        verify(googleApiClientMock).disconnect()
        verify(locationManager).stopLocationUpdates()
    }

    @Test fun isConnectionEstablished_whenGoogleApiClientIsNotConnected_returnsFalse() {

        // ARRANGE

        whenever(googleApiClientMock.isConnected).thenReturn(false)

        // ACT

        val connectionStatus = locationManager.isConnectionEstablished()

        // ASSERT

        assertFalse(connectionStatus)
    }

    @Test fun isConnectionEstablished_whenGoogleApiClientIsConnected_returnsFalse() {

        // ARRANGE

        whenever(googleApiClientMock.isConnected).thenReturn(true)

        // ACT

        val connectionStatus = locationManager.isConnectionEstablished()

        // ASSERT

        assertTrue(connectionStatus)
    }

    // endregion
}