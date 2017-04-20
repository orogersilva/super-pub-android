package com.orogersilva.superpub.dublin.presentation.screen.login

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.orogersilva.superpub.dublin.presentation.screen.login.view.LoginActivity
import javax.inject.Inject

/**
 * Created by orogersilva on 4/13/2017.
 */
class LoginPresenter @Inject constructor(private val loginView: LoginActivity,
                                         private val loginManager: LoginManager,
                                         private val callbackManager: CallbackManager) : LoginContract.Presenter {

    // region INITIALIZER BLOCK

    init {

        loginView.setPresenter(this)

        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onCancel() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSuccess(loginResult: LoginResult?) {

                loginView.goToPubsScreen()
            }

            override fun onError(error: FacebookException?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    // endregion

    // region OVERRIDED METHODS

    override fun resume() {

        if (isLogged()) {
            loginView.goToPubsScreen()
        }
    }

    override fun login() {

        loginManager.logInWithReadPermissions(loginView, listOf("public_profile"))
    }

    override fun isLogged(): Boolean = AccessToken.getCurrentAccessToken() != null

    override fun onResultFromFacebookApi(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    // endregion
}