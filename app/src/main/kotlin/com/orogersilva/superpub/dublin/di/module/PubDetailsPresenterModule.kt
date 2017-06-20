package com.orogersilva.superpub.dublin.di.module

import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.presentation.model.PubModel
import com.orogersilva.superpub.dublin.presentation.screen.pubdetails.PubDetailsContract
import com.orogersilva.superpub.dublin.presentation.screen.pubdetails.PubDetailsPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 6/19/2017.
 */
@ActivityScope
@Module
open class PubDetailsPresenterModule(private val pubDetailsView: PubDetailsContract.View,
                                     private val targetPub: PubModel) {

    // region PROVIDERS

    @Provides @ActivityScope open fun providePubDetailsView() = pubDetailsView

    @Provides @ActivityScope open fun provideTargetPub() = targetPub

    @Provides @ActivityScope open fun providePubDetailsPresenter(): PubDetailsContract.Presenter = PubDetailsPresenter(pubDetailsView, targetPub)

    // endregion
}