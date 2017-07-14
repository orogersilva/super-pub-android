package com.orogersilva.superpub.dublin.adapter.facebook

import android.content.Intent
import com.orogersilva.superpub.dublin.domain.helper.SocialNetworkingHelper
import com.orogersilva.superpub.dublin.adapter.facebook.impl.FacebookAdapterHelper

/**
 * Created by orogersilva on 6/23/2017.
 */
interface FacebookHelper : SocialNetworkingHelper {

    // region OVERRIDED METHODS

    fun isLogged(): Boolean

    fun registerCallback(callbackAdapterService: FacebookAdapterHelper.AdapterCallback)

    fun unregisterCallback()

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    // endregion
}