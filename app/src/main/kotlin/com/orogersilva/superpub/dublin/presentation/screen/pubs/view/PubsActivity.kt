package com.orogersilva.superpub.dublin.presentation.screen.pubs.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.orogersilva.superpub.dublin.R
import com.orogersilva.superpub.dublin.di.modules.*
import com.orogersilva.superpub.dublin.domain.model.Pub
import com.orogersilva.superpub.dublin.presentation.screen.pubs.PubsContract
import com.orogersilva.superpub.dublin.shared.app
import kotlinx.android.synthetic.main.activity_pubs.*
import javax.inject.Inject

/**
 * Created by orogersilva on 4/13/2017.
 */
class PubsActivity : AppCompatActivity(), PubsContract.View {

    // region PROPERTIES

    @Inject lateinit var pubsPresenter: PubsContract.Presenter

    private val pubInfoComponent by lazy {
        app().applicationComponent.newPubRepositoryComponent(PubsPresenterModule(this),
                GetPubsUseCaseModule(), SchedulerProviderModule(), PubRepositoryModule(),
                CacheModule(), DatabaseModule(true), NetworkModule(), ClockModule())
    }

    private val pubs = mutableListOf<Pub>()

    // endregion

    // region ACTIVITY LIFECYCLE METHODS

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pubs)

        customToolbar.title = ""

        setSupportActionBar(customToolbar)

        pubInfoComponent.inject(this)
    }

    override fun onResume() {

        super.onResume()

        pubsPresenter.resume()
    }

    override fun onDestroy() {

        super.onDestroy()

        pubsPresenter.unsubscribe()
    }

    // endregion

    // region OVERRIDED METHODS

    override fun setPresenter(presenter: PubsContract.Presenter) {

        pubsPresenter = presenter
    }

    override fun showLoadingIndicator(isActive: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPubs(pubs: List<Pub?>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // endregion
}