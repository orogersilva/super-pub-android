package com.orogersilva.superpub.dublin.adapter.facebook.impl

import android.content.Intent
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.orogersilva.superpub.dublin.presentation.screen.login.LoginContract
import com.orogersilva.superpub.dublin.presentation.screen.login.view.LoginActivity
import com.orogersilva.superpub.dublin.adapter.facebook.FacebookHelper
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedOutScope
import javax.inject.Inject

/**
 * Created by orogersilva on 6/23/2017.
 */
@LoggedOutScope
class FacebookAdapterHelper @Inject constructor(private val loginView: LoginContract.View,
                                                private val loginManager: LoginManager,
                                                private val callbackManager: CallbackManager) : FacebookHelper {

    // region OVERRIDED METHODS

    override fun login(permissions: List<String>) {

        loginManager.logInWithReadPermissions(loginView as LoginActivity, permissions)
    }

    override fun isLogged(): Boolean = AccessToken.getCurrentAccessToken() != null

    override fun registerCallback(callbackAdapterService: AdapterCallback) {

        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onCancel() {

                callbackAdapterService.onCancel()
            }

            override fun onSuccess(loginResult: LoginResult?) {

                callbackAdapterService.onSuccess()
            }

            override fun onError(error: FacebookException?) {

                callbackAdapterService.onError()
            }
        })
    }

    override fun unregisterCallback() {

        loginManager.unregisterCallback(callbackManager)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    // endregion

    // region INTERFACES

    interface AdapterCallback {

        fun onCancel()

        fun onSuccess()

        fun onError()
    }

    // endregion
}