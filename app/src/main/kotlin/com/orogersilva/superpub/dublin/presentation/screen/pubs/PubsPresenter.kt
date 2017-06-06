package com.orogersilva.superpub.dublin.presentation.screen.pubs

import com.orogersilva.superpub.dublin.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.domain.interactor.GetPubsUseCase
import com.orogersilva.superpub.dublin.domain.model.Pub
import com.orogersilva.superpub.dublin.scheduler.SchedulerProvider
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by orogersilva on 4/21/2017.
 */
@ActivityScope
class PubsPresenter @Inject constructor(private val pubsView: PubsContract.View,
                                        private val getPubsUseCase: GetPubsUseCase,
                                        private val schedulerProvider: SchedulerProvider) : PubsContract.Presenter {

    // endregion

    // region INITIALIZER BLOCK

    init {

        pubsView.setPresenter(this)
    }

    // endregion

    // region PROPERTIES

    internal val pubsList = mutableListOf<Pub?>()

    private var pubsDisposable: Disposable? = null

    // endregion

    // region PUBLIC METHODS

    override fun unsubscribe() {

        pubsDisposable?.let { if (!it.isDisposed) it.dispose() }
        pubsDisposable = null
    }

    // endregion

    // region OVERRIDED METHODS

    override fun resume() {

        refreshPubs(-30.0262844, -51.2072853)
    }

    override fun refreshPubs(lat: Double, lng: Double) {

        getPubsUseCase.getPubs(lat, lng)
                ?.subscribeOn(schedulerProvider.newThread())
                ?.observeOn(schedulerProvider.ui(), true)
                ?.subscribe(object : Observer<Pub> {

                    override fun onNext(pub: Pub?) {

                        pubsList.add(pub)
                    }

                    override fun onComplete() {

                        pubsView.showPubs(pubsList.toList())
                    }

                    override fun onError(error: Throwable?) {

                        pubsView.showErrorMessage()
                    }

                    override fun onSubscribe(disposable: Disposable?) {

                        pubsDisposable = disposable
                    }
                })
    }

    // endregion
}