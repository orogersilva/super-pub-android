package com.orogersilva.superpub.dublin.presentation.screen.pubdetails.view

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.orogersilva.superpub.dublin.R
import com.orogersilva.superpub.dublin.di.component.PubDetailsActivityComponent
import com.orogersilva.superpub.dublin.di.module.PubDetailsPresenterModule
import com.orogersilva.superpub.dublin.presentation.model.PubModel
import com.orogersilva.superpub.dublin.presentation.screen.pubdetails.PubDetailsContract
import com.orogersilva.superpub.dublin.shared.app
import kotlinx.android.synthetic.main.activity_pub_details.*
import javax.inject.Inject

/**
 * Created by orogersilva on 6/18/2017.
 */
class PubDetailsActivity : AppCompatActivity(), PubDetailsContract.View, OnMapReadyCallback {

    // region PROPERTIES

    @Inject lateinit var pubDetailsPresenter: PubDetailsContract.Presenter

    private var pubDetailsActivityComponent: PubDetailsActivityComponent? = null


    private lateinit var pub: PubModel

    // endregion

    // region ACTIVITY LIFECYCLE METHODS

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pub_details)

        customToolbar.title = ""

        setSupportActionBar(customToolbar)

        customToolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        customToolbar.setNavigationOnClickListener { finish() }

        pub = intent.getParcelableExtra<PubModel>("pub_extra")

        pubDetailsActivityComponent = app().loggedInComponent
                ?.newPubDetailsActivityComponent(PubDetailsPresenterModule(this, pub))

        pubDetailsActivityComponent?.inject(this)

        pubDetailsMapView?.onCreate(savedInstanceState)
        pubDetailsMapView?.getMapAsync(this)
    }

    override fun onResume() {

        super.onResume()

        pubDetailsPresenter.resume()
        pubDetailsMapView.onResume()
    }

    override fun onPause() {

        super.onPause()

        pubDetailsMapView.onPause()
    }

    override fun onDestroy() {

        super.onDestroy()

        pubDetailsMapView.onDestroy()
    }

    // endregion

    // region OVERRIDED METHODS

    override fun setPresenter(presenter: PubDetailsContract.Presenter) {

        pubDetailsPresenter = presenter
    }

    override fun showPubDetails(pub: PubModel) {

        if (pub.coverImageUrl != null) {

            Glide.with(this)
                    .load(pub.coverImageUrl)
                    .into(pubDetailsBackgroundImageView)

        } else {

            Glide.with(this)
                    .load(R.drawable.no_background_photo)
                    .into(pubDetailsBackgroundImageView)
        }

        pubNameTextView.text = pub.name

        if (pub.phone.isNullOrEmpty()) {
            pubPhoneTextView.visibility = View.GONE
        } else {
            pubPhoneTextView.text = pub.phone
        }
    }

    override fun onMapReady(map: GoogleMap) {

        val ZOOM_LEVEL = 16.0f

        val latLng = LatLng(pub.latitude, pub.longitude)

        map.uiSettings.isCompassEnabled = true
        map.uiSettings.setAllGesturesEnabled(true)

        if (pub.street != null) {

            map.addMarker(MarkerOptions()
                    .position(latLng)
                    .title(pub.street)
            ).showInfoWindow()

        } else {

            map.addMarker(MarkerOptions()
                    .position(latLng)
                    .title(null)
            )
        }

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL))
    }

    // endregion
}