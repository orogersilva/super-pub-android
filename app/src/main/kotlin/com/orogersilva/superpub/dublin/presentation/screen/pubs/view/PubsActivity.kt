package com.orogersilva.superpub.dublin.presentation.screen.pubs.view

import android.Manifest
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.orogersilva.superpub.dublin.R
import com.orogersilva.superpub.dublin.di.module.*
import com.orogersilva.superpub.dublin.domain.model.Pub
import com.orogersilva.superpub.dublin.presentation.model.PubModel
import com.orogersilva.superpub.dublin.presentation.screen.pubs.PubsContract
import com.orogersilva.superpub.dublin.presentation.screen.pubs.adapter.PubsAdapter
import com.orogersilva.superpub.dublin.shared.app
import com.orogersilva.superpub.dublin.shared.hasPermission
import com.orogersilva.superpub.dublin.shared.permissionsHasBeenGranted
import kotlinx.android.synthetic.main.activity_pubs.*
import javax.inject.Inject

/**
 * Created by orogersilva on 4/13/2017.
 */
class PubsActivity : AppCompatActivity(), PubsContract.View {

    // region PROPERTIES

    @Inject lateinit var pubsPresenter: PubsContract.Presenter

    @Inject lateinit var pubsAdapter: PubsAdapter
    @Inject lateinit var pubsLayoutManager: RecyclerView.LayoutManager

    private val pubsActivityComponent by lazy {

        app().applicationComponent
                .newLoggedinComponent(CacheModule(), ClockModule(), DatabaseModule(true),
                        GoogleApiModule(), LocationSensorModule(), NetworkModule(),
                        SchedulerProviderModule())
                .newPubsActivityComponent(GetPubsUseCaseModule(), GetLastLocationUseCaseModule(),
                        CalculateSuperPubRatingUseCaseModule(), PubRepositoryModule(),
                        PubsAdapterModule(), PubsPresenterModule(this))
    }

    private val ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 1

    private var hasPermissionToAccessDeviceLocation = false

    // endregion

    // region ACTIVITY LIFECYCLE METHODS

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pubs)

        customToolbar.title = ""

        setSupportActionBar(customToolbar)

        pubsActivityComponent.inject(this)

        pubsRecyclerView.adapter = pubsAdapter
        pubsRecyclerView.layoutManager = pubsLayoutManager

        if (!hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                !hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {

                // TODO: SHOULD BE IMPLEMENTED EXPLANATION TO THE USE ABOUT WHY TO USE LOCATION FROM DEVICE.

            } else {

                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION),
                        ACCESS_LOCATION_PERMISSION_REQUEST_CODE)
            }

            return
        }
    }

    override fun onResume() {

        super.onResume()

        if (hasPermissionToAccessDeviceLocation) pubsPresenter.resume()
    }

    override fun onDestroy() {

        super.onDestroy()

        // pubsPresenter.unsubscribe()
    }

    // endregion

    // region OVERRIDED METHODS

    override fun setPresenter(presenter: PubsContract.Presenter) {

        pubsPresenter = presenter
    }

    override fun showLoadingIndicator() {

        loadingView.show()
    }

    override fun hideLoadingIndicator() {

        loadingView.hide()
    }

    override fun refreshPubs(pubs: List<PubModel>) {

        pubsAdapter.replaceData(pubs)
    }

    override fun showErrorMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {

            ACCESS_LOCATION_PERMISSION_REQUEST_CODE -> {

                if (permissionsHasBeenGranted(grantResults)) {

                    pubsPresenter.resume()

                } else {

                    // TODO: Denied permission. To implement.
                }
            }
        }
    }

    // endregion
}