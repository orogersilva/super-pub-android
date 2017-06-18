package com.orogersilva.superpub.dublin.presentation.screen.pubs

import com.orogersilva.superpub.dublin.BaseTestCase
import com.orogersilva.superpub.dublin.domain.interactor.GetLastLocationUseCase
import com.orogersilva.superpub.dublin.domain.interactor.GetPubsUseCase

/**
 * Created by orogersilva on 5/21/2017.
 */
class PubsPresenterTest : BaseTestCase() {

    // region PROPERTIES

    private lateinit var pubsViewMock: PubsContract.View
    private lateinit var getPubsUseCaseMock: GetPubsUseCase
    private lateinit var getLastLocationUseCaseMock: GetLastLocationUseCase

    private lateinit var pubsPresenter: PubsContract.Presenter

    // endregion

    // region SETUP METHODS

    /*@Before fun setup() {

        pubsViewMock = mock<PubsContract.View>()
        getPubsUseCaseMock = mock<GetPubsUseCase>()
        getLastLocationUseCaseMock = mock<GetLastLocationUseCase>()

        pubsPresenter = PubsPresenter(pubsViewMock, getPubsUseCaseMock, getLastLocationUseCaseMock, TestSchedulerProvider())
    }*/

    // endregion

    // region TEST METHODS

    /*@Test fun `Refresh pubs, when location is not valid, then show error message`() {

        // ARRANGE

        val LAT = -90.01
        val LNG = 180.01

        whenever(getPubsUseCaseMock.getPubs(LAT, LNG)).thenReturn(Observable.error(IllegalArgumentException()))

        // ACT

        pubsPresenter.updatePubs(LAT, LNG)

        // ASSERT

        verify(pubsViewMock).showErrorMessage()
    }*/

    /*@Test fun `Refresh pubs, when pubs are emmited with errors, then show error message`() {

        // ARRANGE

        val LAT = 30.0
        val LNG = 30.0

        whenever(getPubsUseCaseMock.getPubs(LAT, LNG)).thenReturn(Observable.error(Exception()))

        // ACT

        pubsPresenter.updatePubs(LAT, LNG)

        // ASSERT

        verify(pubsViewMock).showErrorMessage()
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