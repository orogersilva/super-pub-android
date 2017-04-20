package com.orogersilva.superpub.dublin.presentation.screen.login

import android.content.Intent
import android.hardware.camera2.params.Face
import android.support.v7.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.nhaarman.mockito_kotlin.*
import com.orogersilva.superpub.dublin.di.components.ApplicationComponent
import com.orogersilva.superpub.dublin.di.components.LoginComponent
import com.orogersilva.superpub.dublin.di.modules.FacebookSdkModule
import com.orogersilva.superpub.dublin.di.modules.LoginPresenterModule
import com.orogersilva.superpub.dublin.presentation.screen.login.view.LoginActivity
import it.cosenonjaviste.daggermock.DaggerMockRule
import it.cosenonjaviste.daggermock.InjectFromComponent
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

/**
 * Created by orogersilva on 4/14/2017.
 */
class LoginPresenterTest {

    // region PROPERTIES

    @Rule @JvmField val daggerMockRule: DaggerMockRule<ApplicationComponent> =
            DaggerMockRule(ApplicationComponent::class.java)

    private val loginView: LoginActivity = mock()
    private val loginManager: LoginManager = mock()
    private val callbackManager: CallbackManager = mock()

    @InjectFromComponent(LoginActivity::class) lateinit var loginPresenter: LoginContract.Presenter

    // endregion

    // region TEST METHODS

    @Test fun login_withPublicProfileReadPermissions() {

        // ARRANGE

        val EXPECTED_PERMISSIONS = listOf("public_profile")


        // ACT

        loginPresenter.login()

        // ASSERT

        verify(loginManager).logInWithReadPermissions(loginView, EXPECTED_PERMISSIONS)
    }

    @Test fun onResultFromFacebookApi_cancelledLogin() {

        // ARRANGE

        val REQUEST_CODE = 54090
        val RESULT_CODE = -1
        val INTENT_DATA = null

        // ACT

        loginPresenter.onResultFromFacebookApi(REQUEST_CODE, RESULT_CODE, INTENT_DATA)

        // ASSERT

        callbackManager.onActivityResult(REQUEST_CODE, RESULT_CODE, null)
    }

    @Test fun onResultFromFacebookApi_passedLogin() {

        // ARRANGE

        val REQUEST_CODE = 54090
        val RESULT_CODE = 0
        val INTENT_DATA = null

        // ACT

        loginPresenter.onResultFromFacebookApi(REQUEST_CODE, RESULT_CODE, INTENT_DATA)

        // ASSERT

        callbackManager.onActivityResult(REQUEST_CODE, RESULT_CODE, null)
    }

    // endregion
}