package com.orogersilva.superpub.dublin.presentation.screen.pubs

import com.orogersilva.superpub.dublin.domain.model.Pub
import com.orogersilva.superpub.dublin.presentation.screen.BasePresenter
import com.orogersilva.superpub.dublin.presentation.screen.BaseView

/**
 * Created by orogersilva on 4/21/2017.
 */
interface PubsContract {

    // region INTERFACES

    interface View : BaseView<Presenter> {

        // region METHODS

        fun showLoadingIndicator(isActive: Boolean)
        fun showPubs(pubs: List<Pub?>)
        fun showErrorMessage()

        // endregion
    }

    interface Presenter : BasePresenter {

        // region METHODS

        fun unsubscribe()

        fun refreshPubs()

        // endregion
    }

    // endregion
}