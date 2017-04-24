package com.orogersilva.superpub.dublin.model

import com.google.gson.annotations.SerializedName

/**
 * Created by orogersilva on 4/24/2017.
 */
data class PubHttpResponse(@SerializedName("data") val data: List<Pub>,
                           @SerializedName("paging") val paging: Paging) {

    inner class Paging(@SerializedName("Cursors") val cursors: Cursors,
                       @SerializedName("next") val next: String)

    inner class Cursors(@SerializedName("after") val after: String)
}