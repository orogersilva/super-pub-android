package com.orogersilva.superpub.dublin.data.local

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orogersilva.superpub.dublin.data.entity.PubEntity
import io.reactivex.observers.TestObserver
import io.realm.Realm
import io.realm.RealmConfiguration
import org.junit.After
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/**
 * Created by orogersilva on 5/29/2017.
 */
@RunWith(AndroidJUnit4::class)
class PubLocalDataSourceTest {

    private val DATABASE_NAME = "superpubtest.realm"

    // region SETUP / TEARDOWN CLASS METHODS

    companion object {

        private lateinit var context: Context
        private var pubLocalDataSource: PubLocalDataSource? = null

        @BeforeClass @JvmStatic fun setupClass() {

            context = InstrumentationRegistry.getTargetContext()

            Realm.init(context)

            val realmConfiguration = RealmConfiguration.Builder()
                    .name("superpubtest.realm")
                    .build()

            pubLocalDataSource = PubLocalDataSource(realmConfiguration)
        }

        @AfterClass @JvmStatic fun teardownClass() {

            pubLocalDataSource?.destroyInstance()
            pubLocalDataSource = null
        }
    }

    // endregion

    // region TEST METHODS

    @Test fun getPubs_whenThereAreNotPubs_thenReturnsNoPubs() {

        // ARRANGE

        val QUERY_VALUE = "pub"
        val TYPE_VALUE = "place"
        val FROM_LOCATION_VALUE = "-30.0262844,-51.2072853"
        val DISTANCE_VALUE = 5000
        val LIMIT_VALUE = 200
        val FIELDS_VALUE = "location,name,overall_star_rating,rating_count,checkins,phone,fan_count,picture,cover"

        val EMITTED_EVENTS_COUNT = 0

        val testObserver = TestObserver<PubEntity>()

        // ACT

        pubLocalDataSource?.getPubs(QUERY_VALUE, TYPE_VALUE, FROM_LOCATION_VALUE, DISTANCE_VALUE, LIMIT_VALUE, FIELDS_VALUE)
                ?.subscribe(testObserver)

        // ASSERT

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(EMITTED_EVENTS_COUNT)

        assertTrue(testObserver.values().isEmpty())
    }

    @Test fun getPubs_whenThereArePubs_thenReturnsPubs() {

        // ARRANGE

        val QUERY_VALUE = "pub"
        val TYPE_VALUE = "place"
        val FROM_LOCATION_VALUE = "-30.0262844,-51.2072853"
        val DISTANCE_VALUE = 5000
        val LIMIT_VALUE = 200
        val FIELDS_VALUE = "location,name,overall_star_rating,rating_count,checkins,phone,fan_count,picture,cover"

        val EMITTED_EVENTS_COUNT = 3

        val expectedPubs = createTestData()

        val realmConfiguration = RealmConfiguration.Builder()
                .name(DATABASE_NAME)
                .build()

        val realm = Realm.getInstance(realmConfiguration)

        realm.executeTransaction { it.insertOrUpdate(expectedPubs) }

        realm.close()

        val testObserver = TestObserver<PubEntity>()

        // ACT

        pubLocalDataSource?.getPubs(QUERY_VALUE, TYPE_VALUE, FROM_LOCATION_VALUE, DISTANCE_VALUE, LIMIT_VALUE, FIELDS_VALUE)
                ?.subscribe(testObserver)

        // ASSERT

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(EMITTED_EVENTS_COUNT)

        val pubs = testObserver.values()

        assertEquals(expectedPubs, pubs)
    }

    @Test fun savePubs_whenThereAreNotPubsToBeSaved_thenNoPubsIsPersisted() {

        // ARRANGE

        val expectedPubs = listOf<PubEntity>()

        // ACT

        pubLocalDataSource?.savePubs(expectedPubs)

        // ASSERT

        val realmConfiguration = RealmConfiguration.Builder()
                .name(DATABASE_NAME)
                .build()

        val realm = Realm.getInstance(realmConfiguration)

        val pubs = realm.copyFromRealm(realm.where(PubEntity::class.java).findAll())

        realm.close()

        assertEquals(expectedPubs, pubs)
    }

    @Test fun savePubs_whenThereArePubsToBeSaved_thenPubsArePersistedSuccessfully() {

        // ARRANGE

        val expectedPubs = createTestData()

        // ACT

        pubLocalDataSource?.savePubs(expectedPubs)

        // ASSERT

        val realmConfiguration = RealmConfiguration.Builder()
                .name(DATABASE_NAME)
                .build()

        val realm = Realm.getInstance(realmConfiguration)

        val pubs = realm.copyFromRealm(realm.where(PubEntity::class.java).findAll())

        realm.close()

        assertEquals(expectedPubs, pubs)
    }

    // endregion

    // region TEARDOWN METHODS

    @After fun teardown() {

        pubLocalDataSource?.deletePubs()
    }

    // endregion

    // region UTILITY METHODS

    private fun createTestData(): List<PubEntity> {

        val listType = object : TypeToken<List<PubEntity>>(){}.type

        val pubs = Gson().fromJson<List<PubEntity>>(loadJsonFromAsset("pubs.json"), listType)

        return pubs
    }

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

    // endregion
}