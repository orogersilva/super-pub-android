package com.orogersilva.superpub.dublin.adapter.facebook

import android.content.Intent
import com.orogersilva.superpub.dublin.adapter.SocialNetworkingService
import com.orogersilva.superpub.dublin.adapter.facebook.impl.FacebookAdapterService

/**
 * Created by orogersilva on 6/23/2017.
 */
interface FacebookService : SocialNetworkingService {

    // region OVERRIDED METHODS

    fun isLogged(): Boolean

    fun registerCallback(callbackAdapterService: FacebookAdapterService.AdapterCallback)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    // endregion
}