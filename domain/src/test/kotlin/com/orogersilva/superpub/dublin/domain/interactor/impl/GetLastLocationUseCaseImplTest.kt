package com.orogersilva.superpub.dublin.domain.interactor.impl

import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.orogersilva.superpub.dublin.domain.interactor.GetLastLocationUseCase
import com.orogersilva.superpub.dublin.domain.repository.UserRepository
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test

/**
 * Created by orogersilva on 7/10/2017.
 */
class GetLastLocationUseCaseImplTest {

    // region PROPERTIES

    private lateinit var userRepositoryMock: UserRepository

    private lateinit var getLastLocationUseCase: GetLastLocationUseCase

    // endregion

    // region SETUP METHOD

    @Before fun setup() {

        userRepositoryMock = mock<UserRepository>()

        getLastLocationUseCase = GetLastLocationUseCaseImpl(userRepositoryMock)
    }

    // endregion

    // region TEST METHODS

    @Test fun `Get last location, when there is an unexpected operation, then returns error`() {

        // ARRANGE

        val lastLocationFlowableError = Flowable.error<Pair<Double, Double>>(Exception())

        whenever(userRepositoryMock.getLastLocation()).thenReturn(lastLocationFlowableError)

        val testSubscriber = TestSubscriber<Pair<Double, Double>>()

        // ACT

        getLastLocationUseCase.getLastLocation()
                .subscribe(testSubscriber)

        // ASSERT

        testSubscriber.assertNotComplete()
        testSubscriber.assertError(Exception::class.java)
    }

    @Test fun `Get last location, when there is not a coordinate, then returns default location`() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 1

        val DEFAULT_LAT = 0.0
        val DEFAULT_LNG = 0.0

        val defaultLocationFlowable = Flowable.just(Pair(DEFAULT_LAT, DEFAULT_LNG))

        whenever(userRepositoryMock.getLastLocation()).thenReturn(defaultLocationFlowable)

        val testSubscriber = TestSubscriber<Pair<Double, Double>>()

        // ACT

        getLastLocationUseCase.getLastLocation()
                .subscribe(testSubscriber)

        // ASSERT

        testSubscriber.assertComplete()
                .assertNoErrors()
                .assertValueCount(EMITTED_EVENTS_COUNT)
                .assertOf { l1 ->
                    defaultLocationFlowable.blockingForEach { l2 -> l2.equals(l1) }
                }
    }

    @Test fun `Get last location, when there is a location, then returns itself`() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 1

        val LAT = -90.0
        val LNG = 180.0

        val lastLocationFlowable = Flowable.just(Pair(LAT, LNG))

        whenever(userRepositoryMock.getLastLocation()).thenReturn(lastLocationFlowable)

        val testSubscriber = TestSubscriber<Pair<Double, Double>>()

        // ACT

        getLastLocationUseCase.getLastLocation()
                .subscribe(testSubscriber)

        // ASSERT

        testSubscriber.assertComplete()
                .assertNoErrors()
                .assertValueCount(EMITTED_EVENTS_COUNT)
                .assertOf { l1 ->
                    lastLocationFlowable.blockingForEach { l2 -> l2.equals(l1) }
                }
    }

    // endregion
}