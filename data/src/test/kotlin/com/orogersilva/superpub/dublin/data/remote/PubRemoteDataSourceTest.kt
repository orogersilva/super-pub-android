package com.orogersilva.superpub.dublin.data.remote

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.orogersilva.superpub.dublin.data.BaseTestCase
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.api.endpoint.SearchApiClient
import com.orogersilva.superpub.dublin.data.entity.PubEntity
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

/**
 * Created by orogersilva on 5/29/2017.
 */
class PubRemoteDataSourceTest : BaseTestCase() {

    // region PROPERTIES

    private var apiClientMock: SearchApiClient? = null

    private var pubRemoteDataSource: PubDataSource? = null

    // endregion

    // region SETUP METHODS

    @Before fun setup() {

        apiClientMock = mock<SearchApiClient>()

        pubRemoteDataSource = PubRemoteDataSource(apiClientMock)
    }

    // endregion

    // region TEST METHODS

    @Test fun `Get pubs, when there is no data, then returns no pubs`() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 0

        val QUERY_VALUE = "pub"
        val TYPE_VALUE = "place"
        val FROM_LOCATION_VALUE = "-30.0262844,-51.2072853"
        val DISTANCE_VALUE = 5000
        val LIMIT_VALUE = 200
        val FIELDS_VALUE = "location,name,overall_star_rating,rating_count,checkins,phone,fan_count,picture,cover"

        whenever(apiClientMock?.getPubs(QUERY_VALUE, TYPE_VALUE, FROM_LOCATION_VALUE, DISTANCE_VALUE, LIMIT_VALUE, FIELDS_VALUE)).thenReturn(Observable.empty())

        val testObserver = TestObserver<PubEntity>()

        // ACT

        pubRemoteDataSource?.getPubs(QUERY_VALUE, TYPE_VALUE, FROM_LOCATION_VALUE, DISTANCE_VALUE, LIMIT_VALUE, FIELDS_VALUE)
                ?.subscribe(testObserver)

        // ASSERT


        testObserver.assertComplete()
                .assertNoErrors()
                .assertValueCount(EMITTED_EVENTS_COUNT)
    }

    // TODO: To implement later.
    /*@Test fun `Get pubs, when there is data, then returns pubs`() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 3

        val QUERY_VALUE = "pub"
        val TYPE_VALUE = "place"
        val FROM_LOCATION_VALUE = "-30.0262844,-51.2072853"
        val DISTANCE_VALUE = 5000
        val LIMIT_VALUE = 200
        val FIELDS_VALUE = "location,name,overall_star_rating,rating_count,checkins,phone,fan_count,picture,cover"

        val expectedPubsList = createTestData()

        whenever(apiClientMock?.getPubs(QUERY_VALUE, TYPE_VALUE, FROM_LOCATION_VALUE, DISTANCE_VALUE,
                LIMIT_VALUE, FIELDS_VALUE)).thenReturn(Observable.fromIterable(expectedPubsList))

        val testObserver = TestObserver<PubEntity>()

        // ACT

        // ASSERT
    }*/

    // endregion
}