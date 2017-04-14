package com.orogersilva.superpub.dublin.presentation.screen.login

import android.support.v7.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
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

    private val loginView: LoginActivity = mock()
    private val loginManager: LoginManager = mock()
    private val callbackManager: CallbackManager = mock()

    @Rule @JvmField val daggerMockRule: DaggerMockRule<LoginComponent> =
            DaggerMockRule(LoginComponent::class.java, LoginPresenterModule(loginView), FacebookSdkModule())

    @InjectFromComponent(LoginActivity::class) lateinit var loginPresenter: LoginContract.Presenter

    // endregion

    // region TEST METHODS

    @Test fun login_withPublicProfileReadPermissions() {

        // ARRANGE

        val EXPECTED_PERMISSIONS = listOf("public_profile")

        // ACT

        loginPresenter.login()

        // ASSERT

        verify(loginManager, times(1)).logInWithReadPermissions(loginView as AppCompatActivity, EXPECTED_PERMISSIONS)
    }

    // endregion
}