package com.orogersilva.superpub.dublin.presentation.screen.pubs

import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.domain.interactor.CalculateSuperPubRatingUseCase
import com.orogersilva.superpub.dublin.domain.interactor.GetLastLocationUseCase
import com.orogersilva.superpub.dublin.domain.interactor.GetPubsUseCase
import com.orogersilva.superpub.dublin.domain.model.Pub
import com.orogersilva.superpub.dublin.presentation.model.PubModel
import com.orogersilva.superpub.dublin.presentation.model.mapper.PubModelMapper
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

    private var pubsDisposable: Disposable? = null

    // endregion

    // region OVERRIDED METHODS

    override fun resume() {

        updatePubs()
    }

    override fun updatePubs() {

        getLastLocationUseCase.getLastLocation()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.io())
                .flatMap { (first, second) -> getPubsUseCase.getPubs(-30.0262844, -51.2072853) }        // TODO: Adjust this.
                .collect({ mutableListOf<Pub>() }, { list, pub -> list.add(pub) })
                .flatMapObservable { pubs -> calculateSuperPubRatingUseCase.calculateSuperPubRating(pubs) }
                .observeOn(schedulerProvider.ui(), true)
                .doOnSubscribe { pubsView.showLoadingIndicator() }
                .doOnDispose { pubsView.hideLoadingIndicator() }
                .subscribe(object : Observer<Pub> {

                    override fun onNext(pub: Pub?) {

                        pub?.let { pubsList.add(PubModelMapper.transform(it)) }
                    }

                    override fun onComplete() {

                        pubsView.refreshPubs(pubsList)

                        pubsDisposable?.dispose()
                    }

                    override fun onError(error: Throwable?) {

                        pubsView.showErrorMessage()

                        pubsDisposable?.dispose()
                    }

                    override fun onSubscribe(disposable: Disposable?) {

                        pubsDisposable = disposable
                    }
                })
    }

    // endregion
}