package com.orogersilva.superpub.dublin.presentation.screen.pubdetails

import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.presentation.model.PubModel
import javax.inject.Inject

/**
 * Created by orogersilva on 6/18/2017.
 */
@ActivityScope
class PubDetailsPresenter @Inject constructor(private val pubDetailsView: PubDetailsContract.View,
                                              private val targetPub: PubModel) : PubDetailsContract.Presenter {

    // region INITIALIZER BLOCK

    init {

        pubDetailsView.setPresenter(this)
    }

    // endregion

    // region OVERRIDED METHODS

    override fun resume() {

        setPubDetailsOnScreen()
    }

    override fun destroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // endregion

    // region UTILITY METHODS

    private fun setPubDetailsOnScreen() {

        pubDetailsView.showPubDetails(targetPub)
    }

    // endregion
}