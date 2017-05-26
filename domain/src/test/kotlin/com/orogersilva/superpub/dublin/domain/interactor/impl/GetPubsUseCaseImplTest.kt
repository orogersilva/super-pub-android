package com.orogersilva.superpub.dublin.domain.interactor.impl

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.orogersilva.superpub.dublin.domain.BaseTestCase
import com.orogersilva.superpub.dublin.domain.interactor.GetPubsUseCase
import com.orogersilva.superpub.dublin.domain.model.Pub
import com.orogersilva.superpub.dublin.domain.repository.PubRepository
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
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

    @Test fun getPubs_whenLocationIsNotValid_throwsIllegalArgumentException() {

        // ARRANGE

        val LAT = -90.01
        val LNG = 180.01

        val testObserver = TestObserver<Pub>()

        // ACT

        getPubsUseCase.getPubs(LAT, LNG)
                ?.subscribe(testObserver)

        // ASSERT

        testObserver.assertNotComplete()
        testObserver.assertError(IllegalArgumentException::class.java)
    }

    @Test fun getPubs_whenLocationIsValid_returnsPubs() {

        // ARRANGE

        val LAT = -90.0
        val LNG = 180.0

        val EMITTED_EVENTS_COUNT = 3

        val pubsObservable = Observable.fromIterable(createTestData())

        whenever(pubRepositoryMock.getPubs(fromLocation = "$LAT,$LNG")).thenReturn(pubsObservable)

        val testObserver = TestObserver<Pub>()

        // ACT

        getPubsUseCase.getPubs(LAT, LNG)
                ?.subscribe(testObserver)

        // ASSERT

        testObserver.assertComplete()
                .assertNoErrors()
                .assertValueCount(EMITTED_EVENTS_COUNT)
                .assertOf { p1 ->
                    pubsObservable.blockingForEach { p2 -> p2.equals(p1) }
                }
    }

    // endregion
}