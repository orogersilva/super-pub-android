package com.orogersilva.superpub.dublin.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by orogersilva on 5/28/2017.
 */
data class PubHttpResponse(@SerializedName("data") val data: List<PubData>,
                           @SerializedName("paging") val paging: Paging) {

    inner class PubData(@SerializedName("id") val id: Long,
                        @SerializedName("location") val location: Location,
                        @SerializedName("name") val name: String,
                        @SerializedName("overall_star_rating") val rating: Double,
                        @SerializedName("rating_count") val ratingCount: Int,
                        @SerializedName("checkins") val checkins: Int,
                        @SerializedName("phone") val phone: String?,
                        @SerializedName("fan_count") val fanCount: Int,
                        @SerializedName("picture") val picture: Picture,
                        @SerializedName("cover") val cover: Cover?)

    inner class Location(@SerializedName("city") val city: String,
                         @SerializedName("country") val country: String,
                         @SerializedName("latitude") val latitude: Double,
                         @SerializedName("longitude") val longitude: Double,
                         @SerializedName("state") val state: String,
                         @SerializedName("street") val street: String,
                         @SerializedName("zip") val zipCode: String)

    inner class Picture(@SerializedName("data") val picturedata: PictureData)

    inner class PictureData(@SerializedName("is_silhouette") val isSilhouette: Boolean,     // No available photo.
                            @SerializedName("url") val url: String)

    inner class Cover(@SerializedName("cover_id") val coverId: Long,
                      @SerializedName("offset_x") val offsetX: Long,
                      @SerializedName("offset_y") val offsetY: Long,
                      @SerializedName("source") val source: String,
                      @SerializedName("id") val id: Long)

    inner class Paging(@SerializedName("Cursors") val cursors: Cursors,
                       @SerializedName("next") val next: String)

    inner class Cursors(@SerializedName("after") val after: String)
}