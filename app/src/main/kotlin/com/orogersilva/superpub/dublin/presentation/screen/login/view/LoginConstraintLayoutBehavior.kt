package com.orogersilva.superpub.dublin.presentation.screen.login.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.View

/**
 * Created by orogersilva on 7/10/2017.
 */
class LoginConstraintLayoutBehavior : CoordinatorLayout.Behavior<ConstraintLayout> {

    // region CONSTRUCTORS

    @Suppress("UNUSED_PARAMETER")
    constructor(context: Context, attributeSet: AttributeSet) {}

    // endregion

    // region OVERRIDED METHODS

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: ConstraintLayout?, dependency: View?): Boolean =
            dependency is Snackbar.SnackbarLayout

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: ConstraintLayout, dependency: View): Boolean {

        val translationY = Math.min(0f, dependency.translationY - dependency.height)

        child.translationY = translationY

        return true
    }

    // endregion
}