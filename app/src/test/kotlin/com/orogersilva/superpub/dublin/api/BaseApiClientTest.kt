package com.orogersilva.superpub.dublin.api

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.AfterClass
import org.junit.BeforeClass
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/**
 * Created by orogersilva on 4/23/2017.
 */
open class BaseApiClientTest {

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

    protected fun loadJsonFromAsset(fileName: String): String? {

        var jsonStr: String?
        var inputStream: InputStream? = null

        try {

            inputStream = javaClass.classLoader.getResourceAsStream(fileName)

            val size = inputStream.available()
            val buffer = ByteArray(size)

            inputStream.read(buffer)

            jsonStr = String(buffer, Charset.forName("UTF-8"))

        } catch (ex: IOException) {

            ex.printStackTrace()

            return null

        } finally {

            inputStream?.close()
        }

        return jsonStr
    }

    // endregion

    // region TEARDOWN METHODS

    @After fun teardown() {

        server?.shutdown()
    }

    // endregion
}