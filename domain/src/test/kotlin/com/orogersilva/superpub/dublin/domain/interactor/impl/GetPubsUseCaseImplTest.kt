package com.orogersilva.superpub.dublin.domain.interactor.impl

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.orogersilva.superpub.dublin.domain.BaseTestCase
import com.orogersilva.superpub.dublin.domain.interactor.GetPubsUseCase
import com.orogersilva.superpub.dublin.domain.model.Pub
import com.orogersilva.superpub.dublin.domain.repository.PubRepository
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test

/**
 * Created by orogersilva on 5/26/2017.
 */
class GetPubsUseCaseImplTest : BaseTestCase() {

    // region PROPERTIES

    private lateinit var pubRepositoryMock: PubRepository

    private lateinit var getPubsUseCase: GetPubsUseCase

    // endregion

    // region SETUP

    @Before fun setup() {

        pubRepositoryMock = mock<PubRepository>()

        getPubsUseCase = GetPubsUseCaseImpl(pubRepositoryMock)
    }

    // endregion

    // region TEST METHODS

    @Test fun `Get pubs, when location is not valid, then throws IllegalArgumentException`() {

        // ARRANGE

        val LAT = -90.01
        val LNG = 180.01

        val testSubscriber = TestSubscriber<Pub>()

        // ACT

        getPubsUseCase.getPubs(LAT, LNG)
                .subscribe(testSubscriber)

        // ASSERT

        testSubscriber.assertNotComplete()
        testSubscriber.assertError(IllegalArgumentException::class.java)
    }

    @Test fun `Get pubs, when location is valid, then returns pubs`() {

        // ARRANGE

        val RESOURCES_FILE_NAME = "pubs.json"

        val LAT = -90.0
        val LNG = 180.0

        val EMITTED_EVENTS_COUNT = 3

        val pubsObservable = Flowable.fromIterable(createTestData(loadJsonFromAsset(RESOURCES_FILE_NAME)))

        whenever(pubRepositoryMock.getPubs(fromLocation = "$LAT,$LNG")).thenReturn(pubsObservable)

        val testSubscriber = TestSubscriber<Pub>()

        // ACT

        getPubsUseCase.getPubs(LAT, LNG)
                .subscribe(testSubscriber)

        // ASSERT

        testSubscriber.assertComplete()
                .assertNoErrors()
                .assertValueCount(EMITTED_EVENTS_COUNT)
                .assertOf { p1 ->
                    pubsObservable.blockingForEach { p2 -> p2.equals(p1) }
                }
    }

    // endregion
}