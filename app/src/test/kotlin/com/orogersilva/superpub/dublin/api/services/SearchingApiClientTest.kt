package com.orogersilva.superpub.dublin.api.services

import com.orogersilva.superpub.dublin.api.BaseApiClientTest
import com.orogersilva.superpub.dublin.api.HttpLocalResponseDispatcher
import com.orogersilva.superpub.dublin.api.RestClient
import com.orogersilva.superpub.dublin.api.endpoint.SearchingApiClient
import com.orogersilva.superpub.dublin.model.Pub
import com.orogersilva.superpub.dublin.model.PubHttpResponse
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

/**
 * Created by orogersilva on 4/23/2017.
 */
class SearchingApiClientTest : BaseApiClientTest() {

    // region TEST METHODS

    @Test fun getPubs_correctRequest() {

        // ARRANGE

        val QUERY_VALUE = "pub"
        val TYPE_VALUE = "place"
        val CENTER_VALUE = "-30.0262844,-51.2072853"
        val DISTANCE_VALUE = 5000
        val LIMIT_VALUE = 200
        val FIELDS_VALUE = "location,name,overall_star_rating,rating_count,checkins,phone,fan_count,picture,cover"

        val EXPECTED_PATH = "/search?q=$QUERY_VALUE&type=$TYPE_VALUE&center=$CENTER_VALUE&distance=$DISTANCE_VALUE&limit=$LIMIT_VALUE&fields=$FIELDS_VALUE"

        server?.setDispatcher(HttpLocalResponseDispatcher(loadJsonFromAsset("pubs.json")))
        server?.start()

        val testObserver = TestObserver<PubHttpResponse>()

        // ACT

        getSearchingApiClient()
                .getPubs(QUERY_VALUE, TYPE_VALUE, CENTER_VALUE, DISTANCE_VALUE, LIMIT_VALUE, FIELDS_VALUE)
                .subscribe(testObserver)

        // ASSERT

        val recordedRequest = server?.takeRequest()

        assertEquals(EXPECTED_PATH, recordedRequest?.path)

        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

    // endregion

    // region UTILITY METHODS

    private fun getSearchingApiClient() = RestClient.getApiClient(SearchingApiClient::class.java, getBaseEndpoint())

    // endregion
}