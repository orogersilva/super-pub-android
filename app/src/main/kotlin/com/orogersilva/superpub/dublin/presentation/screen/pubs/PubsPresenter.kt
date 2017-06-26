package com.orogersilva.superpub.dublin.presentation.screen.pubs

import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.domain.interactor.CalculateSuperPubRatingUseCase
import com.orogersilva.superpub.dublin.domain.interactor.GetLastLocationUseCase
import com.orogersilva.superpub.dublin.domain.interactor.GetPubsUseCase
import com.orogersilva.superpub.dublin.domain.model.Pub
import com.orogersilva.superpub.dublin.presentation.model.PubModel
import com.orogersilva.superpub.dublin.presentation.model.mapper.PubModelMapper
import com.orogersilva.superpub.dublin.scheduler.SchedulerProvider
import io.reactivex.subscribers.ResourceSubscriber
import javax.inject.Inject

/**
 * Created by orogersilva on 4/21/2017.
 */
@ActivityScope
class PubsPresenter @Inject constructor(private val pubsView: PubsContract.View,
                                        private val getPubsUseCase: GetPubsUseCase,
                                        private val getLastLocationUseCase: GetLastLocationUseCase,
                                        private val calculateSuperPubRatingUseCase: CalculateSuperPubRatingUseCase,
                                        private val schedulerProvider: SchedulerProvider) : PubsContract.Presenter {

    // endregion

    // region INITIALIZER BLOCK

    init {

        pubsView.setPresenter(this)
    }

    // endregion

    // region PROPERTIES

    internal val pubsList = mutableListOf<PubModel>()

    // endregion

    // region OVERRIDED METHODS

    override fun resume() {

        updatePubs()
    }

    override fun updatePubs() {

        var forceUpdate = pubsView.isRefreshManual()

        getLastLocationUseCase.getLastLocation()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.io())
                .flatMap { (first, second) -> getPubsUseCase.getPubs(first, second, forceUpdate) }
                .collect({ mutableListOf<Pub>() }, { list, pub -> list.add(pub) })
                .flatMapPublisher { pubs -> calculateSuperPubRatingUseCase.calculateSuperPubRating(pubs) }
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { if (!pubsView.isRefreshManual()) pubsView.showLoadingIndicator() }
                .doOnCancel {

                    pubsList.clear()

                    if (pubsView.isRefreshManual()) {
                        pubsView.hideRefreshManualIndicator()
                    } else {
                        pubsView.hideLoadingIndicator()
                    }
                }
                .subscribe(object : ResourceSubscriber<Pub>() {

                    override fun onNext(pub: Pub?) {

                        pub?.let { pubsList.add(PubModelMapper.transform(it)) }
                    }

                    override fun onComplete() {

                        pubsView.refreshPubs(pubsList)

                        dispose()
                    }

                    override fun onError(t: Throwable?) {

                        pubsView.showErrorMessage()

                        dispose()
                    }
                })
    }

    // endregion
}