package com.orogersilva.superpub.dublin.data.local

import android.content.SharedPreferences
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.orogersilva.superpub.dublin.domain.local.PreferencesDataSource
import io.reactivex.subscribers.TestSubscriber
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by orogersilva on 7/4/2017.
 */
class UserPreferencesDataSourceTest {

    // region PROPERTIES

    private lateinit var sharedPreferencesMock: SharedPreferences
    private lateinit var sharedPreferencesEditorMock: SharedPreferences.Editor

    private val LAT_PREF_KEY = "LAT_PREF_KEY"
    private val LNG_PREF_KEY = "LNG_PREF_KEY"

    private var userPreferencesDataSource: PreferencesDataSource? = null

    // endregion

    // region SETUP METHOD

    @Before fun setup() {

        sharedPreferencesMock = mock<SharedPreferences>()
        sharedPreferencesEditorMock = mock<SharedPreferences.Editor>()

        userPreferencesDataSource = UserPreferencesDataSource(sharedPreferencesMock,
                sharedPreferencesEditorMock, LAT_PREF_KEY, LNG_PREF_KEY)
    }

    // endregion

    // region TEST METHODS

    @Test fun `Get last location, when there is no data, then returns default value`() {

        // ARRANGE

        val LAT_DEFAULT_LONG_VALUE = 0L
        val LNG_DEFAULT_LONG_VALUE = 0L

        val EXPECTED_LAT_VALUE = 0.0
        val EXPECTED_LNG_VALUE = 0.0

        val EXPECTED_LAST_LOCATION = Pair(EXPECTED_LAT_VALUE, EXPECTED_LNG_VALUE)

        whenever(sharedPreferencesMock.getLong(LAT_PREF_KEY, LAT_DEFAULT_LONG_VALUE)).thenReturn(LAT_DEFAULT_LONG_VALUE)
        whenever(sharedPreferencesMock.getLong(LNG_PREF_KEY, LNG_DEFAULT_LONG_VALUE)).thenReturn(LNG_DEFAULT_LONG_VALUE)

        // ACT

        val lastLocation = userPreferencesDataSource?.getLastLocation()

        // ASSERT

        assertEquals(EXPECTED_LAST_LOCATION, lastLocation)
    }

    @Test fun `Get last location, when there is data, then returns last location`() {

        // ARRANGE

        val LAT_DEFAULT_LONG_VALUE = 0L
        val LNG_DEFAULT_LONG_VALUE = 0L
        val EXPECTED_LAT_LONG_VALUE = -4594216138805409725
        val EXPECTED_LNG_LONG_VALUE = -4590964107078524037

        val EXPECTED_LAT_VALUE = -30.0654803
        val EXPECTED_LNG_VALUE = -51.2380358

        val EXPECTED_LAST_LOCATION = Pair(EXPECTED_LAT_VALUE, EXPECTED_LNG_VALUE)

        whenever(sharedPreferencesMock.getLong(LAT_PREF_KEY, LAT_DEFAULT_LONG_VALUE)).thenReturn(EXPECTED_LAT_LONG_VALUE)
        whenever(sharedPreferencesMock.getLong(LNG_PREF_KEY, LNG_DEFAULT_LONG_VALUE)).thenReturn(EXPECTED_LNG_LONG_VALUE)

        val testSubscriber = TestSubscriber<Pair<Double, Double>>()

        // ACT

        val lastLocation = userPreferencesDataSource?.getLastLocation()

        // ASSERT

        assertEquals(EXPECTED_LAST_LOCATION, lastLocation)
    }

    // endregion

    // region TEARDOWN METHOD

    @After fun teardown() {

        userPreferencesDataSource?.clear()
        userPreferencesDataSource = null
    }

    // endregion
}