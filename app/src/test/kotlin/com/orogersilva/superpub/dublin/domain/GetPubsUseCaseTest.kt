package com.orogersilva.superpub.dublin.domain

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.orogersilva.superpub.dublin.BaseTestCase
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.PubRepository
import com.orogersilva.superpub.dublin.domain.impl.GetPubsUseCaseImpl
import com.orogersilva.superpub.dublin.model.Pub
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

/**
 * Created by orogersilva on 5/17/2017.
 */
class GetPubsUseCaseTest : BaseTestCase() {

    // region PROPERTIES

    private lateinit var pubRepositoryMock: PubDataSource

    private lateinit var getPubsUseCase: GetPubsUseCase

    // endregion

    // region SETUP METHODS

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