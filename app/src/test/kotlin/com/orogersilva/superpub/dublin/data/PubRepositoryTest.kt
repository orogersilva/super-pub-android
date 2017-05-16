package com.orogersilva.superpub.dublin.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockito_kotlin.*
import com.orogersilva.superpub.dublin.data.local.PubLocalDataSource
import com.orogersilva.superpub.dublin.data.remote.PubRemoteDataSource
import com.orogersilva.superpub.dublin.model.Pub
import com.orogersilva.superpub.dublin.shared.toImmutableMap
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.*
import org.junit.Assert.assertEquals
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/**
 * Created by orogersilva on 4/28/2017.
 */
class PubRepositoryTest {

    // region PROPERTIES

    private var pubLocalDataSourceMock: PubDataSource? = null
    private var pubRemoteDataSourceMock: PubDataSource? = null

    private var pubRepository: PubRepository? = null

    // endregion

    // region SETUP METHODS

    @Before fun setup() {

        pubLocalDataSourceMock = mock<PubLocalDataSource>()
        pubRemoteDataSourceMock = mock<PubRemoteDataSource>()

        pubRepository = spy(PubRepository(pubLocalDataSourceMock, pubRemoteDataSourceMock))
    }

    // endregion

    // region TEST METHODS

    @Test fun getPubs_whenThereIsNotDataLocally_retrieveDataFromNetworkLayer() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 3
        val CENTER_VALUE = "-30.0262844,-51.2072853"

        val expectedPubsList = createTestData()

        val diskData = Observable.empty<Pub>()
        val networkData = Observable.fromIterable(expectedPubsList)

        whenever(pubLocalDataSourceMock?.getPubs(center = CENTER_VALUE)).thenReturn(diskData)
        whenever(pubRemoteDataSourceMock?.getPubs(center = CENTER_VALUE)).thenReturn(networkData)

        doNothing().whenever(pubLocalDataSourceMock)?.savePubs(expectedPubsList, true)

        val testObserver = TestObserver<Pub>()

        // ACT

        pubRepository?.getPubs(center = CENTER_VALUE)
                ?.subscribe(testObserver)

        // ASSERT

        testObserver.awaitTerminalEvent()

        for (expectedPub in expectedPubsList) {
            verify(pubRepository)?.cacheInMemory(expectedPub)
        }

        // verify(pubRepository, times(1))?.saveToDisk(pubRepository.cachedPubs)

        assertEquals(expectedPubsList.size, pubRepository?.cachedPubs?.size)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(EMITTED_EVENTS_COUNT)
    }

    @Test fun getPubs_whenThereIsDataNotCachedLocally_retrieveDataFromDatabaseLayer() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 3
        val CENTER_VALUE = "-30.0262844,-51.2072853"

        val expectedPubsList = createTestData()

        expectedPubsList.map { it.timestamp = System.currentTimeMillis() }

        val diskData = Observable.fromIterable(expectedPubsList)
        val networkData = Observable.empty<Pub>()

        whenever(pubLocalDataSourceMock?.getPubs(center = CENTER_VALUE)).thenReturn(diskData)
        whenever(pubRemoteDataSourceMock?.getPubs(center = CENTER_VALUE)).thenReturn(networkData)

        val testObserver = TestObserver<Pub>()

        // ACT

        pubRepository?.getPubs(center = CENTER_VALUE)
                ?.subscribe(testObserver)

        // ASSERT

        testObserver.awaitTerminalEvent()

        for (expectedPub in expectedPubsList) {
            verify(pubRepository)?.cacheInMemory(expectedPub)
        }

        assertEquals(expectedPubsList.size, pubRepository?.cachedPubs?.size)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(EMITTED_EVENTS_COUNT)
    }

    /*@Test fun getPubs_whenThereIsDataCachedLocally_retrieveDataFromCache() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 3
        val CENTER_VALUE = "-30.0262844,-51.2072853"

        val expectedPubsList = createTestData()

        for (expectedPub in expectedPubsList) {
            pubRepository?.cachedPubs?.put(expectedPub.id, expectedPub)
        }

        val diskData = Observable.empty<Pub>()
        val networkData = Observable.empty<Pub>()

        whenever(pubLocalDataSourceMock?.getPubs(center = CENTER_VALUE)).thenReturn(diskData)
        whenever(pubRemoteDataSourceMock?.getPubs(center = CENTER_VALUE)).thenReturn(networkData)

        val testObserver = TestObserver<Pub>()

        // ACT

        pubRepository?.getPubs(center = CENTER_VALUE)
                ?.subscribe(testObserver)

        // ASSERT

        testObserver.awaitTerminalEvent()

        assertEquals(expectedPubsList.size, pubRepository?.cachedPubs?.size)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(EMITTED_EVENTS_COUNT)
    }*/

    // endregion

    // region TEARDOWN METHODS

    @After fun teardown() {

        pubRepository?.destroyInstance()
        pubRepository = null
    }

    // endregion

    // region UTILITY METHODS

    private fun createTestData(): List<Pub> {

        val listType = object : TypeToken<List<Pub>>(){}.type

        val pubs = Gson().fromJson<List<Pub>>(loadJsonFromAsset("pubs.json"), listType)

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