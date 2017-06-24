package com.orogersilva.superpub.dublin.presentation.screen.login

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.orogersilva.superpub.dublin.adapter.facebook.FacebookService
import com.orogersilva.superpub.dublin.presentation.screen.login.view.LoginActivity
import org.junit.Before
import org.junit.Test

/**
 * Created by orogersilva on 4/14/2017.
 */
class LoginPresenterTest {

    // region PROPERTIES

    private lateinit var loginViewMock: LoginActivity
    private lateinit var facebookAdapterServiceMock: FacebookService

    private lateinit var loginPresenter: LoginPresenter

    // endregion

    // region SETUP METHODS

    @Before fun setup() {

        loginViewMock = mock()
        facebookAdapterServiceMock = mock()

        loginPresenter = LoginPresenter(loginViewMock, facebookAdapterServiceMock)
    }

    // endregion

    // region TEST METHODS

    @Test fun `Login with public profile read permissions`() {

        // ARRANGE

        val EXPECTED_PERMISSIONS = listOf("public_profile")

        // ACT

        loginPresenter.login()

        // ASSERT

        verify(facebookAdapterServiceMock).login(EXPECTED_PERMISSIONS)
    }

    @Test fun `OnResult from Facebook API cancelled login`() {

        // ARRANGE

        val REQUEST_CODE = 54090
        val RESULT_CODE = -1
        val INTENT_DATA = null

        // ACT

        loginPresenter.onResultFromFacebookService(REQUEST_CODE, RESULT_CODE, INTENT_DATA)

        // ASSERT

        verify(facebookAdapterServiceMock).onActivityResult(REQUEST_CODE, RESULT_CODE, INTENT_DATA)
    }

    @Test fun `OnResult from Facebook API passed login`() {

        // ARRANGE

        val REQUEST_CODE = 54090
        val RESULT_CODE = 0
        val INTENT_DATA = null

        // ACT

        loginPresenter.onResultFromFacebookService(REQUEST_CODE, RESULT_CODE, INTENT_DATA)

        // ASSERT

        verify(facebookAdapterServiceMock).onActivityResult(REQUEST_CODE, RESULT_CODE, INTENT_DATA)
    }

    // endregion
}