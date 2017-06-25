package com.orogersilva.superpub.dublin.data.repository

import com.nhaarman.mockito_kotlin.*
import com.orogersilva.superpub.dublin.data.BaseTestCase
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.cache.PubCache
import com.orogersilva.superpub.dublin.data.entity.PubEntity
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

    private lateinit var pubCacheMock: PubCache

    private var pubLocalDataSourceMock: PubDataSource? = null
    private var pubRemoteDataSourceMock: PubDataSource? = null

    private var pubDataRepository: PubDataRepository? = null

    // endregion

    // region SETUP METHOD

    @Before fun setup() {

        pubCacheMock = mock<PubCache>()
        pubLocalDataSourceMock = mock<PubDataSource>()
        pubRemoteDataSourceMock = mock<PubDataSource>()

        pubDataRepository = PubDataRepository(pubCacheMock, pubLocalDataSourceMock, pubRemoteDataSourceMock)
    }

    // endregion

    // region TEST METHODS

    @Test fun `Get pubs, when there is no data from any sources, then returns no pub`() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 0
        val FROM_LOCATION_VALUE = "-30.0262844,-51.2072853"

        val cachedData = listOf<PubEntity>()
        val diskData = Observable.empty<List<PubEntity>>()
        val networkData = Observable.empty<List<PubEntity>>()

        whenever(pubCacheMock.getPubs()).thenReturn(cachedData)
        whenever(pubLocalDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(diskData)
        whenever(pubRemoteDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(networkData)

        val testObserver = TestObserver<Pub>()

        // ACT

        pubDataRepository?.getPubs(fromLocation = FROM_LOCATION_VALUE)
                ?.subscribe(testObserver)

        // ASSERT

        verify(pubCacheMock, times(1)).clear()
        verify(pubLocalDataSourceMock, times(1))?.deletePubs()

        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
                .assertNoErrors()
                .assertValueCount(EMITTED_EVENTS_COUNT)
    }

    @Test fun `Get pubs, when there is cached data, then returns pubs from cache`() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 3
        val FROM_LOCATION_VALUE = "-30.0262844,-51.2072853"

        val expectedPubsList = mutableListOf<PubEntity>()

        expectedPubsList.addAll(createTestData(loadJsonFromAsset(RESOURCES_FILE_NAME)))

        val cachedData = expectedPubsList
        val diskData = Observable.empty<List<PubEntity>>()
        val networkData = Observable.empty<List<PubEntity>>()

        whenever(pubCacheMock.getPubs()).thenReturn(cachedData)
        whenever(pubLocalDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(diskData)
        whenever(pubRemoteDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(networkData)

        val testObserver = TestObserver<Pub>()

        // ACT

        pubDataRepository?.getPubs(fromLocation = FROM_LOCATION_VALUE, getDataFromRemote = false)
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

        val expectedPubsList = mutableListOf<PubEntity>()

        expectedPubsList.addAll(createTestData(loadJsonFromAsset(RESOURCES_FILE_NAME)))

        val cachedData = listOf<PubEntity>()
        val diskData = Observable.just(expectedPubsList.toList())
        val networkData = Observable.empty<List<PubEntity>>()

        whenever(pubCacheMock.getPubs()).thenReturn(cachedData)
        whenever(pubLocalDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(diskData)
        whenever(pubRemoteDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(networkData)

        val testObserver = TestObserver<Pub>()

        // ACT

        pubDataRepository?.getPubs(fromLocation = FROM_LOCATION_VALUE, getDataFromRemote = false)
                ?.subscribe(testObserver)

        // ASSERT

        testObserver.awaitTerminalEvent()

        verify(pubCacheMock, times(1)).savePubs(expectedPubsList)

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

        val expectedPubsList = mutableListOf<PubEntity>()

        expectedPubsList.addAll(createTestData(loadJsonFromAsset(RESOURCES_FILE_NAME)))

        val cachedData = listOf<PubEntity>()
        val diskData = Observable.empty<List<PubEntity>>()
        val networkData = Observable.just(expectedPubsList.toList())

        whenever(pubCacheMock.getPubs()).thenReturn(cachedData)
        whenever(pubLocalDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(diskData)
        whenever(pubRemoteDataSourceMock?.getPubs(fromLocation = FROM_LOCATION_VALUE)).thenReturn(networkData)

        val testObserver = TestObserver<Pub>()

        // ACT

        pubDataRepository?.getPubs(fromLocation = FROM_LOCATION_VALUE)
                ?.subscribe(testObserver)

        // ASSERT

        verify(pubCacheMock, times(1)).clear()
        verify(pubLocalDataSourceMock, times(1))?.deletePubs()

        testObserver.awaitTerminalEvent()

        verify(pubCacheMock, times(1)).savePubs(expectedPubsList)

        verify(pubCacheMock, times(2)).getPubs()
        verify(pubLocalDataSourceMock, times(1))?.savePubs(any())   // TODO: Review this.

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