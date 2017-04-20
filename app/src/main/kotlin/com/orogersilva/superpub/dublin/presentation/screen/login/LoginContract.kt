package com.orogersilva.superpub.dublin.presentation.screen.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.orogersilva.superpub.dublin.presentation.screen.BasePresenter
import com.orogersilva.superpub.dublin.presentation.screen.BaseView

/**
 * Created by orogersilva on 4/9/2017.
 */
interface LoginContract {

    // region INTERFACES

    interface View : BaseView<Presenter> {

        // region METHODS

        fun goToPubsScreen()

        // endregion
    }

    interface Presenter : BasePresenter {

        // region METHODS

        fun login()
        fun isLogged(): Boolean
        fun onResultFromFacebookApi(requestCode: Int, resultCode: Int, data: Intent?)

        // endregion
    }

    // endregion
}