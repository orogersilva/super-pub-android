package com.orogersilva.superpub.dublin.presentation.screen.login

import android.app.Activity
import android.content.Intent
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

/**
 * Created by orogersilva on 4/13/2017.
 */
class LoginPresenter(private val loginView: LoginContract.View,
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

        loginManager.logInWithReadPermissions(loginView as Activity, listOf("public_profile"))
    }

    override fun isLogged(): Boolean = AccessToken.getCurrentAccessToken() != null

    override fun onScreenResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    // endregion
}