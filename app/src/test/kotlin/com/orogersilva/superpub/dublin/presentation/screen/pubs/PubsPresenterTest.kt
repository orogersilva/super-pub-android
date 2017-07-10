package com.orogersilva.superpub.dublin.presentation.screen.pubs

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.orogersilva.superpub.dublin.BaseTestCase
import com.orogersilva.superpub.dublin.TestSchedulerProvider
import com.orogersilva.superpub.dublin.domain.interactor.CalculateSuperPubRatingUseCase
import com.orogersilva.superpub.dublin.domain.interactor.GetLastLocationUseCase
import com.orogersilva.superpub.dublin.domain.interactor.GetPubsUseCase
import com.orogersilva.superpub.dublin.scheduler.SchedulerProvider
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

/**
 * Created by orogersilva on 5/21/2017.
 */
class PubsPresenterTest : BaseTestCase() {

    // region PROPERTIES

    private lateinit var pubsViewMock: PubsContract.View
    private lateinit var getPubsUseCaseMock: GetPubsUseCase
    private lateinit var getLastLocationUseCaseMock: GetLastLocationUseCase
    private lateinit var calculateSuperPubRatingUseCase: CalculateSuperPubRatingUseCase
    private lateinit var schedulerProvider: SchedulerProvider

    private lateinit var pubsPresenter: PubsContract.Presenter

    // endregion

    // region SETUP METHODS

    @Before fun setup() {

        pubsViewMock = mock<PubsContract.View>()
        getPubsUseCaseMock = mock<GetPubsUseCase>()
        getLastLocationUseCaseMock = mock<GetLastLocationUseCase>()
        calculateSuperPubRatingUseCase = mock<CalculateSuperPubRatingUseCase>()
        schedulerProvider = TestSchedulerProvider()

        pubsPresenter = PubsPresenter(pubsViewMock, getPubsUseCaseMock, getLastLocationUseCaseMock,
                calculateSuperPubRatingUseCase, schedulerProvider)
    }

    // endregion

    // region TEST METHODS





























    /*@Test fun `Update pubs, when location is not valid, then show error message`() {

        // ARRANGE

        val LAT = -90.01
        val LNG = 180.01

        whenever(getPubsUseCaseMock.getPubs(LAT, LNG)).thenReturn(Observable.error(IllegalArgumentException()))

        // ACT

        pubsPresenter.updatePubs(LAT, LNG)

        // ASSERT

        verify(pubsViewMock).showLoginErrorMessage()
    }*/

    /*@Test fun `Refresh pubs, when pubs are emmited with errors, then show error message`() {

        // ARRANGE

        val LAT = 30.0
        val LNG = 30.0

        whenever(getPubsUseCaseMock.getPubs(LAT, LNG)).thenReturn(Observable.error(Exception()))

        // ACT

        pubsPresenter.updatePubs(LAT, LNG)

        // ASSERT

        verify(pubsViewMock).showLoginErrorMessage()
    }*/

    /*@Test fun `Refresh pubs, when pubs are emmited with success, then show pubs`() {

        // ARRANGE

        val LAT = 30.0
        val LNG = 30.0

        val expectedData = createTestData()

        val pubsObservable = Observable.fromIterable(expectedData)

        whenever(getPubsUseCaseMock.getPubs(LAT, LNG)).thenReturn(pubsObservable)

        // ACT

        pubsPresenter.updatePubs(LAT, LNG)

        // ASSERT

        assertEquals(expectedData, (pubsPresenter as PubsPresenter).pubsList)

        verify(pubsViewMock).refreshPubs(expectedData)
    }*/

    // endregion
}