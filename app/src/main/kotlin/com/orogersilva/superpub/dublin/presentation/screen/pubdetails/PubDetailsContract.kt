package com.orogersilva.superpub.dublin.presentation.screen.pubdetails

import com.orogersilva.superpub.dublin.presentation.model.PubModel
import com.orogersilva.superpub.dublin.presentation.screen.BasePresenter
import com.orogersilva.superpub.dublin.presentation.screen.BaseView

/**
 * Created by orogersilva on 6/18/2017.
 */
interface PubDetailsContract {

    // region INTERFACES

    interface View : BaseView<Presenter> {

        // region METHODS

        fun showPubDetails(pub: PubModel)

        // endregion
    }

    interface Presenter : BasePresenter {

        // region METHODS



        // endregion
    }

    // endregion
}