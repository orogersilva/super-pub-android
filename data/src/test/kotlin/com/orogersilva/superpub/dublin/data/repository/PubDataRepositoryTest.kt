package com.orogersilva.superpub.dublin.data.repository

import com.nhaarman.mockito_kotlin.*
import com.orogersilva.superpub.dublin.data.BaseTestCase
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.cache.PubCache
import com.orogersilva.superpub.dublin.data.entity.PubEntity
import com.orogersilva.superpub.dublin.data.shared.date.Clock
import com.orogersilva.superpub.dublin.domain.model.Pub
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by orogersilva on 5/28/2017.
 */
class PubDataRepositoryTest : BaseTestCase() {

    // region PROPERTIES

    private val RESOURCES_FILE_NAME = "pubs.json"

    private var pubCacheMock: PubCache? = null
    private var pubLocalDataSourceMock: PubDataSource? = null
    private var pubRemoteDataSourceMock: PubDataSource? = null
    private lateinit var clockMock: Clock

    private var pubDataRepository: PubDataRepository? = null

    // endregion

    // region SETUP METHOD

    @Before fun setup() {

        pubCacheMock = mock<PubCache>()
        pubLocalDataSourceMock = mock<PubDataSource>()
        pubRemoteDataSourceMock = mock<PubDataSource>()
        clockMock = mock<Clock>()

        pubDataRepository = PubDataRepository(pubCacheMock, pubLocalDataSourceMock, pubRemoteDataSourceMock, clockMock)
    }

    // endregion

    // region TEST METHODS

    @Test fun `Get pubs, when there is no data from any sources, thenr returns no pub`() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 0
        val FROM_LOCATION_VALUE = "-30.0262844,-51.2072853"

        val cachedData = listOf<PubEntity>()
        val diskData = Observable.empty<PubEntity>()
        val networkData = Observable.empty<PubEntity>()

        whenever(pubCacheMock?.getPubs()).thenReturn(cachedData)
        whenever(pubLocalDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(diskData)
        whenever(pubRemoteDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(networkData)

        val testObserver = TestObserver<Pub>()

        // ACT

        pubDataRepository?.getPubs(fromLocation = FROM_LOCATION_VALUE)
                ?.subscribe(testObserver)

        // ASSERT

        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
                .assertNoErrors()
                .assertValueCount(EMITTED_EVENTS_COUNT)
    }

    @Test fun `Get pubs, when there is cached data, then returns pubs from cache`() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 3
        val FROM_LOCATION_VALUE = "-30.0262844,-51.2072853"
        val EXPECTED_CURRENT_TIME_MILLIS = 1496014380458L

        val expectedPubsList = mutableListOf<PubEntity>()

        expectedPubsList.addAll(createTestData(loadJsonFromAsset(RESOURCES_FILE_NAME)))
        expectedPubsList.forEach { it.timestamp = EXPECTED_CURRENT_TIME_MILLIS }

        val cachedData = expectedPubsList
        val diskData = Observable.empty<PubEntity>()
        val networkData = Observable.empty<PubEntity>()

        whenever(clockMock.getCurrentTimeMillis()).thenReturn(EXPECTED_CURRENT_TIME_MILLIS)

        whenever(pubCacheMock?.getPubs()).thenReturn(cachedData)
        whenever(pubLocalDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(diskData)
        whenever(pubRemoteDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(networkData)

        val testObserver = TestObserver<Pub>()

        // ACT

        pubDataRepository?.getPubs(fromLocation = FROM_LOCATION_VALUE)
                ?.subscribe(testObserver)

        // ASSERT

        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
                .assertNoErrors()
                .assertValueCount(EMITTED_EVENTS_COUNT)
                .assertOf { pub1 ->
                    Observable.fromIterable(cachedData).blockingForEach { pub2 -> (pub1 == pub2) }
                }
    }

    @Test fun `Get pubs, when there is data just in database, then cache data and returns pubs`() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 3
        val FROM_LOCATION_VALUE = "-30.0262844,-51.2072853"
        val EXPECTED_CURRENT_TIME_MILLIS = 1496014380458L

        val expectedPubsList = mutableListOf<PubEntity>()

        expectedPubsList.addAll(createTestData(loadJsonFromAsset(RESOURCES_FILE_NAME)))
        expectedPubsList.forEach { it.timestamp = EXPECTED_CURRENT_TIME_MILLIS }

        val cachedData = listOf<PubEntity>()
        val diskData = Observable.fromIterable(expectedPubsList)
        val networkData = Observable.empty<PubEntity>()

        whenever(clockMock.getCurrentTimeMillis()).thenReturn(EXPECTED_CURRENT_TIME_MILLIS)

        whenever(pubCacheMock?.getPubs()).thenReturn(cachedData)
        whenever(pubLocalDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(diskData)
        whenever(pubRemoteDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(networkData)

        val testObserver = TestObserver<Pub>()

        // ACT

        pubDataRepository?.getPubs(fromLocation = FROM_LOCATION_VALUE)
                ?.subscribe(testObserver)

        // ASSERT

        testObserver.awaitTerminalEvent()

        expectedPubsList.forEach {
            verify(pubCacheMock, times(1))?.savePub(it)
        }

        testObserver.assertComplete()
                .assertNoErrors()
                .assertValueCount(EMITTED_EVENTS_COUNT)
                .assertOf { pub1 ->
                    diskData.blockingForEach { pub2 -> (pub1 == pub2) }
                }
    }

    @Test fun `Get pubs, when there is data coming from network, then persist data and returns pubs`() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 3
        val FROM_LOCATION_VALUE = "-30.0262844,-51.2072853"
        val EXPECTED_CURRENT_TIME_MILLIS = 1496014380458L

        val expectedPubsList = mutableListOf<PubEntity>()

        expectedPubsList.addAll(createTestData(loadJsonFromAsset(RESOURCES_FILE_NAME)))
        expectedPubsList.forEach { it.timestamp = EXPECTED_CURRENT_TIME_MILLIS }

        val cachedData = listOf<PubEntity>()
        val diskData = Observable.empty<PubEntity>()
        val networkData = Observable.fromIterable(expectedPubsList)

        whenever(clockMock.getCurrentTimeMillis()).thenReturn(EXPECTED_CURRENT_TIME_MILLIS)

        whenever(pubCacheMock?.getPubs()).thenReturn(cachedData)
        whenever(pubLocalDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(diskData)
        whenever(pubRemoteDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(networkData)

        val testObserver = TestObserver<Pub>()

        // ACT

        pubDataRepository?.getPubs(fromLocation = FROM_LOCATION_VALUE)
                ?.subscribe(testObserver)

        // ASSERT

        testObserver.awaitTerminalEvent()

        verify(clockMock, times(expectedPubsList.size)).getCurrentTimeMillis()

        expectedPubsList.forEach {
            verify(pubCacheMock, times(1))?.savePub(it)
        }

        verify(pubCacheMock, times(2))?.getPubs()
        verify(pubLocalDataSourceMock, times(1))?.savePubs(any())   // TODO: Review this code snippet.

        testObserver.assertComplete()
                .assertNoErrors()
                .assertValueCount(EMITTED_EVENTS_COUNT)
                .assertOf { pub1 ->
                    networkData.blockingForEach { pub2 -> (pub1 == pub2) }
                }
    }

    // endregion

    // region TEARDOWN METHOD

    @After fun teardown() {

        pubDataRepository?.destroyInstance()
        pubDataRepository = null
    }

    // endregion
}