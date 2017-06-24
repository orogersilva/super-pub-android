package com.orogersilva.superpub.dublin.presentation.screen.login

import android.content.Intent
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

        fun onResultFromFacebookService(requestCode: Int, resultCode: Int, data: Intent?)

        // endregion
    }

    // endregion
}