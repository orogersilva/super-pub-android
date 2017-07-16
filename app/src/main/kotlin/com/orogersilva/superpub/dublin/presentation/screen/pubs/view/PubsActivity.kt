package com.orogersilva.superpub.dublin.presentation.screen.pubs.view

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentSender
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.google.android.gms.common.api.ResolvableApiException
import com.orogersilva.superpub.dublin.R
import com.orogersilva.superpub.dublin.device.location.LocationBroadcastReceiver
import com.orogersilva.superpub.dublin.device.location.LocationService
import com.orogersilva.superpub.dublin.di.component.PubsActivityComponent
import com.orogersilva.superpub.dublin.di.module.*
import com.orogersilva.superpub.dublin.presentation.model.PubModel
import com.orogersilva.superpub.dublin.presentation.screen.pubs.PubsContract
import com.orogersilva.superpub.dublin.presentation.screen.pubs.adapter.PubsAdapter
import com.orogersilva.superpub.dublin.shared.PermissionUtils
import com.orogersilva.superpub.dublin.shared.app
import com.orogersilva.superpub.dublin.shared.hasPermission
import kotlinx.android.synthetic.main.activity_pubs.*
import javax.inject.Inject

/**
 * Created by orogersilva on 4/13/2017.
 */
class PubsActivity : AppCompatActivity(), PubsContract.View,
        PermissionUtils.PermissionDeniedDialog.PermissionDeniedDialogListener,
        PermissionUtils.RationaleDialog.RationaleDialogListener {

    // region PROPERTIES

    @Inject lateinit var pubsPresenter: PubsContract.Presenter

    @Inject lateinit var pubsAdapter: PubsAdapter
    @Inject lateinit var pubsLayoutManager: RecyclerView.LayoutManager

    @Inject lateinit var locationBroadcastReceiver: LocationBroadcastReceiver

    private lateinit var locationServiceIntent: Intent

    private var pubsActivityComponent: PubsActivityComponent? = null

    private val ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 1
    private val APPLICATION_DETAILS_SETTINGS_REQUEST_CODE = 2
    private val CHECK_SETTINGS_REQUEST_CODE = 3

    private lateinit var permissionDeniedDialog: DialogFragment
    private lateinit var rationaleDialog: DialogFragment

    private var hasPermissionToAccessDeviceLocation = false

    // endregion

    // region ACTIVITY LIFECYCLE METHODS

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pubs)

        customToolbar.title = ""

        setSupportActionBar(customToolbar)

        pubsActivityComponent = app().createLoggedInComponent(true)
                ?.newPubsActivityComponent(GetPubsUseCaseModule(), GetLastLocationUseCaseModule(),
                        CalculateSuperPubRatingUseCaseModule(), PubRepositoryModule(),
                        PubsAdapterModule(), PubsLocationBroadcastReceiverModule(), PubsPresenterModule(this), UserRepositoryModule())

        pubsActivityComponent?.inject(this)

        pubsRecyclerView.adapter = pubsAdapter
        pubsRecyclerView.layoutManager = pubsLayoutManager

        pubsSwipeRefreshLayout.setOnRefreshListener { pubsPresenter.resume() }
        pubsSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.gold), ContextCompat.getColor(this, R.color.black)
        )

        locationServiceIntent = Intent(this, LocationService::class.java)
    }

    override fun onStart() {

        super.onStart()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                    !hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {

                PermissionUtils.requestPermissions(this, ACCESS_LOCATION_PERMISSION_REQUEST_CODE,
                        listOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION), getString(
                        R.string.location_permission_rationale_dialog_title), getString(
                        R.string.location_permission_rationale_dialog_message), getString(R.string.yes),
                        getString(R.string.cancel), false)

            } else {

                hasPermissionToAccessDeviceLocation = true
            }

        } else {

            hasPermissionToAccessDeviceLocation = true
        }

        val locationIntentFilter = IntentFilter()

        locationIntentFilter.addAction(getString(R.string.get_location_action))
        locationIntentFilter.addAction(getString(R.string.set_location_settings_action))

        registerReceiver(locationBroadcastReceiver, locationIntentFilter)

        if (hasPermissionToAccessDeviceLocation) startService(locationServiceIntent)
    }

    override fun onResume() {

        super.onResume()

        if (hasPermissionToAccessDeviceLocation) pubsPresenter.resume()
    }

    override fun onPause() {

        super.onPause()
    }

    override fun onStop() {

        super.onStop()

        stopService(locationServiceIntent)

        unregisterReceiver(locationBroadcastReceiver)
    }

    override fun onDestroy() {

        super.onDestroy()

        pubsActivityComponent = null
    }

    // endregion

    // region OVERRIDED METHODS

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {

            CHECK_SETTINGS_REQUEST_CODE -> {

                when (resultCode) {

                    AppCompatActivity.RESULT_OK -> {

                        startService(locationServiceIntent)

                        pubsPresenter.resume()
                    }

                    AppCompatActivity.RESULT_CANCELED -> {

                        // TODO: To implement.
                    }
                }
            }
        }
    }

    override fun setPresenter(presenter: PubsContract.Presenter) {

        pubsPresenter = presenter
    }

    override fun showLoadingIndicator() {

        loadingView.show()
    }

    override fun hideLoadingIndicator() {

        loadingView.hide()
    }

    override fun isRefreshManual(): Boolean = pubsSwipeRefreshLayout.isRefreshing

    override fun hideRefreshManualIndicator() {

        pubsSwipeRefreshLayout.isRefreshing = false
    }

    override fun refreshPubs(pubs: List<PubModel>) {

        pubsAdapter.replaceData(pubs)
    }

    override fun showErrorMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun goToLocationSettingsScreen(throwable: Throwable?) {

        try {

            val locationSettingsResolvableApiException = throwable as ResolvableApiException

            locationSettingsResolvableApiException.startResolutionForResult(this, CHECK_SETTINGS_REQUEST_CODE)

        } catch (sendIntentException: IntentSender.SendIntentException) {

            // TODO: To implement.
        }
    }

    override fun onPermissionDeniedDialogEnablingButtonClicked() {

        showApplicationDetailsSettingsPane()
    }

    override fun onRationaleDialogPositiveButtonClicked(requestCode: Int, permissions: List<String>) {

        PermissionUtils.requestPermissionsWithoutAskForRequestPermissionRationale(this, permissions, requestCode)
    }

    override fun onRationaleDialogNegativeButtonClicked() {

        showPermissionDeniedDialog()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode != ACCESS_LOCATION_PERMISSION_REQUEST_CODE) return

        if (PermissionUtils.isPermissionsGranted(grantResults.toList())) {

            startService(locationServiceIntent)

            pubsPresenter.resume()

        } else {

            if (PermissionUtils.shouldShowRequestPermissionRationale(this, permissions.toList())) {

                showPermissionRationaleDialog(permissions.toList(), requestCode)

            }else {

                // "Don't show again" has been checked.
                showPermissionDeniedDialog()
            }
        }
    }

    // endregion

    // region UTILITY METHODS

    private fun showPermissionRationaleDialog(permissions: List<String>, requestCode: Int) {

        rationaleDialog = PermissionUtils.RationaleDialog.newInstance(getString(
                R.string.location_permission_rationale_dialog_title),
                getString(R.string.location_permission_rationale_dialog_message),
                getString(R.string.yes), getString(R.string.cancel),
                permissions.toList(), requestCode, false)

        supportFragmentManager
                .beginTransaction()
                .add(rationaleDialog, null)
                .commitAllowingStateLoss()
    }

    private fun showPermissionDeniedDialog() {

        permissionDeniedDialog = PermissionUtils.PermissionDeniedDialog.newInstance(
                getString(R.string.location_denied_permission_dialog_title),
                getString(R.string.location_denied_permission_dialog_message),
                getString(R.string.location_enabling_label), false)

        supportFragmentManager
                .beginTransaction()
                .add(permissionDeniedDialog, null)
                .commitAllowingStateLoss()
    }

    private fun showApplicationDetailsSettingsPane() {

        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)

        intent.data = Uri.fromParts("package", packageName, null)

        startActivityForResult(intent, APPLICATION_DETAILS_SETTINGS_REQUEST_CODE)
    }

    // endregion
}