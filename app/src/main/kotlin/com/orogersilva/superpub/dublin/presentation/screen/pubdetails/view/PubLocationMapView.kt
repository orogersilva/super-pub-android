package com.orogersilva.superpub.dublin.presentation.screen.pubdetails.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewParent
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView

/**
 * Created by orogersilva on 6/24/2017.
 */
class PubLocationMapView : MapView {

    // region PROPERTIES

    var viewParent: ViewParent? = null

    // endregion

    // region CONSTRUCTORS

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, i: Int) : super(context, attributeSet, i)

    constructor(context: Context, googleMapOptions: GoogleMapOptions) : super(context, googleMapOptions)

    // endregion

    // region OVERRIDED METHODS

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {

            MotionEvent.ACTION_UP, MotionEvent.ACTION_DOWN -> {

                if (viewParent == null) {
                    parent.requestDisallowInterceptTouchEvent(true)
                } else {
                    viewParent?.requestDisallowInterceptTouchEvent(true)
                }
            }
        }

        return super.onInterceptTouchEvent(event)
    }

    // endregion
}