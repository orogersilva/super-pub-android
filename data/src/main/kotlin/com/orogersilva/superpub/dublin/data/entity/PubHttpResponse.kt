package com.orogersilva.superpub.dublin.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by orogersilva on 5/28/2017.
 */
data class PubHttpResponse(@SerializedName("data") val data: List<PubEntity>,
                           @SerializedName("paging") val paging: Paging) {

    inner class Paging(@SerializedName("Cursors") val cursors: Cursors,
                       @SerializedName("next") val next: String)

    inner class Cursors(@SerializedName("after") val after: String)
}