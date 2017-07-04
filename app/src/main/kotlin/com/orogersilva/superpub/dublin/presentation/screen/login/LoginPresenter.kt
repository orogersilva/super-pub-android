package com.orogersilva.superpub.dublin.presentation.screen.login

import android.content.Intent
import com.orogersilva.superpub.dublin.adapter.facebook.FacebookHelper
import com.orogersilva.superpub.dublin.adapter.facebook.impl.FacebookAdapterHelper
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import javax.inject.Inject

/**
 * Created by orogersilva on 4/13/2017.
 */
@ActivityScope
class LoginPresenter @Inject constructor(private val loginView: LoginContract.View,
                                         private val facebookAdapterService: FacebookHelper) : LoginContract.Presenter {

    // region INITIALIZER BLOCK

    init {

        loginView.setPresenter(this)

        facebookAdapterService.registerCallback(object : FacebookAdapterHelper.AdapterCallback {

            override fun onCancel() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSuccess() {

                loginView.goToPubsScreen()
            }

            override fun onError() {
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

        facebookAdapterService.login(listOf("public_profile"))
    }

    override fun isLogged(): Boolean = facebookAdapterService.isLogged()

    override fun onResultFromFacebookService(requestCode: Int, resultCode: Int, data: Intent?) {

        facebookAdapterService.onActivityResult(requestCode, resultCode, data)
    }

    // endregion
}