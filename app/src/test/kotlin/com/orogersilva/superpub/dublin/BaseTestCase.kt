package com.orogersilva.superpub.dublin

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orogersilva.superpub.dublin.model.Pub
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/**
 * Created by orogersilva on 5/17/2017.
 */
open class BaseTestCase {

    // region PROTECTED METHODS

    protected fun createTestData(): List<Pub> {

        val listType = object : TypeToken<List<Pub>>(){}.type

        val pubs = Gson().fromJson<List<Pub>>(loadJsonFromAsset("pubs.json"), listType)

        return pubs
    }

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
}