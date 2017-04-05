package com.orogersilva.superpub.dublin.data.local

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.model.Pub
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.realm.Realm
import io.realm.RealmConfiguration
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/**
 * Created by orogersilva on 3/31/2017.
 */
@RunWith(AndroidJUnit4::class)
class PubLocalDataSourceTest {

    // region SETUP / TEARDOWN CLASS METHODS

    companion object {

        private lateinit var context: Context
        private var pubLocalDataSource: PubDataSource? = null

        @BeforeClass @JvmStatic fun setupClass() {

            context = InstrumentationRegistry.getTargetContext()

            Realm.init(context)

            val realmConfiguration = RealmConfiguration.Builder()
                    .name("superpubtest.realm")
                    .build()

            val realm = Realm.getInstance(realmConfiguration)

            pubLocalDataSource = PubLocalDataSource.getInstance(realm)
        }

        @AfterClass @JvmStatic fun teardownClass() {

            PubLocalDataSource.destroyInstance()
            pubLocalDataSource = null
        }
    }

    // endregion

    // region TEST METHODS

    @Test fun getPubs_whenDatabaseIsClean_returnsEmptyListOfPubs() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 1

        val testObserver = TestObserver<List<Pub>>()

        // ACT

        pubLocalDataSource?.getPubs()
                ?.subscribe(testObserver)

        // ASSERT

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(EMITTED_EVENTS_COUNT)

        assertTrue(testObserver.values()[0].isEmpty())
    }

    @Test fun getPubs_whenThereArePubs_returnsPubs() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 1

        val expectedPubs = createTestData()

        val realmConfiguration = RealmConfiguration.Builder()
                .name("superpubtest.realm")
                .build()

        val realm = Realm.getInstance(realmConfiguration)

        realm.executeTransaction {

            expectedPubs.forEach {

                realm.insert(it)
            }
        }

        realm.close()

        val testObserver = TestObserver<List<Pub>>()

        // ACT

        pubLocalDataSource?.getPubs()
                ?.subscribe(testObserver)

        // ASSERT

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(EMITTED_EVENTS_COUNT)

        val pubs = testObserver.values()[0]

        assertEquals(expectedPubs, pubs)
    }

    // endregion

    // region TEARDOWN METHODS

    @After fun teardown() {

        pubLocalDataSource?.deletePubs()
    }

    // endregion

    // region UTILITY METHODS

    private fun loadJsonFromAsset(fileName: String): String? {

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

    private fun createTestData(): List<Pub> {

        val listType = object : TypeToken<List<Pub>>(){}.type

        val pubs = Gson().fromJson<List<Pub>>(loadJsonFromAsset("pubs.json"), listType)

        return pubs
    }

    // endregion
}