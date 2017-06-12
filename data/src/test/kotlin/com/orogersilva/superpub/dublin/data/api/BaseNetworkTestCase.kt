package com.orogersilva.superpub.dublin.data.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orogersilva.superpub.dublin.data.BaseTestCase
import com.orogersilva.superpub.dublin.data.entity.PubHttpResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.AfterClass
import org.junit.BeforeClass

/**
 * Created by orogersilva on 5/28/2017.
 */
open class BaseNetworkTestCase : BaseTestCase() {

    // region SETUP / TEARDOWN CLASS METHODS

    companion object {

        var server: MockWebServer? = null

        @JvmField val OK_STATUS_CODE = 200

        @BeforeClass @JvmStatic fun setupClass() {

            server = MockWebServer()
        }

        @AfterClass @JvmStatic fun teardownClass() {

            server = null
        }
    }

    // endregion

    // region PROTECTED METHODS

    protected fun getBaseEndpoint() = server?.url("/").toString()

    protected fun createTestHttpData(jsonStr: String?): PubHttpResponse {

        val listType = object : TypeToken<PubHttpResponse>(){}.type

        val pubs = Gson().fromJson<PubHttpResponse>(jsonStr, listType)

        return pubs
    }

    // endregion

    // region TEARDOWN METHODS

    @After fun teardown() {

        server?.shutdown()
    }

    // endregion
}