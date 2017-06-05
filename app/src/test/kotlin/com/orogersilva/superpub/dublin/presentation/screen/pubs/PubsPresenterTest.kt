package com.orogersilva.superpub.dublin.presentation.screen.pubs

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.orogersilva.superpub.dublin.BaseTestCase
import com.orogersilva.superpub.dublin.TestSchedulerProvider
import com.orogersilva.superpub.dublin.domain.interactor.GetPubsUseCase
import com.orogersilva.superpub.dublin.presentation.screen.pubs.view.PubsActivity
import com.orogersilva.superpub.dublin.scheduler.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.*
import org.junit.Assert.assertEquals

/**
 * Created by orogersilva on 5/21/2017.
 */
class PubsPresenterTest : BaseTestCase() {

    // region PROPERTIES

    private lateinit var pubsViewMock: PubsContract.View
    private lateinit var getPubsUseCaseMock: GetPubsUseCase

    private lateinit var pubsPresenter: PubsContract.Presenter

    // endregion

    // region SETUP METHODS

    @Before fun setup() {

        pubsViewMock = mock<PubsContract.View>()
        getPubsUseCaseMock = mock<GetPubsUseCase>()

        pubsPresenter = PubsPresenter(pubsViewMock, getPubsUseCaseMock, TestSchedulerProvider())
    }

    // endregion

    // region TEST METHODS

    @Test fun `Refresh pubs, when location is not valid, then show error message`() {

        // ARRANGE

        val LAT = -90.01
        val LNG = 180.01

        whenever(getPubsUseCaseMock.getPubs(LAT, LNG)).thenReturn(Observable.error(IllegalArgumentException()))

        // ACT

        pubsPresenter.refreshPubs(LAT, LNG)

        // ASSERT

        verify(pubsViewMock).showErrorMessage()
    }

    @Test fun `Refresh pubs, when pubs are emmited with errors, then show error message`() {

        // ARRANGE

        val LAT = 30.0
        val LNG = 30.0

        whenever(getPubsUseCaseMock.getPubs(LAT, LNG)).thenReturn(Observable.error(Exception()))

        // ACT

        pubsPresenter.refreshPubs(LAT, LNG)

        // ASSERT

        verify(pubsViewMock).showErrorMessage()
    }

    @Test fun `Refresh pubs, when pubs are emmited with success, then show pubs`() {

        // ARRANGE

        val LAT = 30.0
        val LNG = 30.0

        val expectedData = createTestData()

        val pubsObservable = Observable.fromIterable(expectedData)

        whenever(getPubsUseCaseMock.getPubs(LAT, LNG)).thenReturn(pubsObservable)

        // ACT

        pubsPresenter.refreshPubs(LAT, LNG)

        // ASSERT

        assertEquals(expectedData, (pubsPresenter as PubsPresenter).pubsList)

        verify(pubsViewMock).showPubs(expectedData)
    }

    // endregion
}