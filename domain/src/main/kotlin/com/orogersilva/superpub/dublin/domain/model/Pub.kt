package com.orogersilva.superpub.dublin.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by orogersilva on 5/26/2017.
 */
data class Pub(@SerializedName("id") var id: Long = 0,
          @SerializedName("name") var name: String = "",
          @SerializedName("phone") var phone: String? = null,
          @SerializedName("rating") var rating: Double = 0.0,
          @SerializedName("ratingCount") var ratingCount: Int = 0,
          @SerializedName("checkins") var checkins: Int = 0,
          @SerializedName("likes") var likes: Int = 0,
          @SerializedName("coverImageUrl") var coverImageUrl: String? = null,
          @SerializedName("pictureImageUrl") var pictureImageUrl: String? = null,
          @SerializedName("latitude") var latitude: Double = 0.0,
          @SerializedName("longitude") var longitude: Double = 0.0,
          @SerializedName("street") var street: String? = null,
          @SerializedName("hasMinimumRequirement") var hasMinimumRequirement: Boolean = false,
          var timestamp: Long = 0L) : Comparable<Pub> {

    // region PROPERTIES

    // var superPubRating: Double = 0.0

    // endregion

    // region OVERRIDED METHODS

    // override fun compareTo(other: Pub) = other.superPubRating.compareTo(superPubRating)
    override fun compareTo(other: Pub) = 0

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val pub = other as Pub

        return id == pub.id &&
                name.equals(pub.name) &&
                phone == pub.phone &&
                Math.abs(rating - pub.rating) < 0.01 &&
                ratingCount == pub.ratingCount &&
                checkins == pub.checkins &&
                likes == pub.likes &&
                coverImageUrl == pub.coverImageUrl &&
                pictureImageUrl == pub.pictureImageUrl &&
                Math.abs(latitude - pub.latitude) < 0.0000001 &&
                Math.abs(longitude - pub.longitude) < 0.0000001 &&
                street == pub.street &&
                hasMinimumRequirement == pub.hasMinimumRequirement &&
                timestamp == pub.timestamp
    }

    // endregion
}