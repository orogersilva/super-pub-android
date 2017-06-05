package com.orogersilva.superpub.dublin.data.api.endpoint

import com.orogersilva.superpub.dublin.data.api.BaseNetworkTestCase
import com.orogersilva.superpub.dublin.data.api.HttpLocalResponseDispatcher
import com.orogersilva.superpub.dublin.data.api.RestClient
import com.orogersilva.superpub.dublin.data.entity.PubHttpResponse
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Test

/**
 * Created by orogersilva on 5/28/2017.
 */
class SearchApiClientTest : BaseNetworkTestCase() {

    // region TEST METHODS

    @Test fun `Get pubs, correct request`() {

        // ARRANGE

        val QUERY_VALUE = "pub"
        val TYPE_VALUE = "place"
        val CENTER_VALUE = "-30.0262844,-51.2072853"
        val DISTANCE_VALUE = 5000
        val LIMIT_VALUE = 200
        val FIELDS_VALUE = "location,name,overall_star_rating,rating_count,checkins,phone,fan_count,picture,cover"

        val EXPECTED_PATH = "/search?q=$QUERY_VALUE&type=$TYPE_VALUE&center=$CENTER_VALUE&distance=$DISTANCE_VALUE&limit=$LIMIT_VALUE&fields=$FIELDS_VALUE"

        server?.setDispatcher(HttpLocalResponseDispatcher(loadJsonFromAsset("pubsHttpResponse.json")))
        server?.start()

        val testObserver = TestObserver<PubHttpResponse>()

        // ACT

        getSearchingApiClient()
                .getPubs(QUERY_VALUE, TYPE_VALUE, CENTER_VALUE, DISTANCE_VALUE, LIMIT_VALUE, FIELDS_VALUE)
                .subscribe(testObserver)

        // ASSERT

        val recordedRequest = server?.takeRequest()

        Assert.assertEquals(EXPECTED_PATH, recordedRequest?.path)

        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

    // endregion

    // region UTILITY METHODS

    private fun getSearchingApiClient() = RestClient.getApiClient(SearchApiClient::class.java, getBaseEndpoint())

    // endregion
}