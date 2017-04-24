package com.orogersilva.superpub.dublin.presentation.screen

/**
 * Created by orogersilva on 4/21/2017.
 */
interface ItemView<in T> {

    // region METHODS

    fun setItem(item: T)

    // endregion
}