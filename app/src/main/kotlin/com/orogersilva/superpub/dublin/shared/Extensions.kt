package com.orogersilva.superpub.dublin.shared

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.orogersilva.superpub.dublin.SuperPubApplication
import com.orogersilva.superpub.dublin.domain.model.Pub
import java.util.*

/**
 * Created by orogersilva on 4/14/2017.
 */

// region ACTIVITY EXTENSION METHODS

fun AppCompatActivity.app(): SuperPubApplication = application as SuperPubApplication

fun AppCompatActivity.hasPermission(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun AppCompatActivity.permissionsHasBeenGranted(grantResults: IntArray): Boolean {

    grantResults.forEach { if (it != PackageManager.PERMISSION_GRANTED) return false }

    return true
}

fun <T> AppCompatActivity.intentFor(cls: Class<T>) = Intent(this, cls)

// endregion

// region MUTABLE LIST<Pub> EXTENSION METHODS

fun MutableList<Pub>.calculateSuperPubRating() {

    if (isEmpty()) return

    val MINIMUM_RATING_COUNT_ALLOWED = 15
    val MINIMUM_SUPER_PUB_RATING_ALLOWED = 1

    val RATING_PARAMETER_WEIGHT: Double = 4.0
    val RATING_COUNT_PARAMETER_WEIGHT: Double = 0.2
    val CHECKINS_PARAMETER_WEIGHT: Double = 0.5
    val LIKES_PARAMETER_WEIGHT = 0.3

    val MAX_RATING = 5

    // region 1. FIND PUBS WITH HIGHEST NUMBER OF CHECKINS AND LIKES.

    var maxRatingCount = 0
    var maxCheckins = 0
    var maxLikes = 0

    forEach {

        if (it.ratingCount > maxRatingCount) maxRatingCount = it.ratingCount
        if (it.checkins > maxCheckins) maxCheckins = it.checkins
        if (it.likes > maxLikes) maxLikes = it.likes
    }

    // endregion

    // region 2. CALCULATE "SUPER PUB" RATING.

    if (maxRatingCount == 0) maxRatingCount = 1
    if (maxCheckins == 0) maxCheckins = 1
    if (maxLikes == 0) maxLikes = 1

    forEach {

        var superPubRating = RATING_PARAMETER_WEIGHT * (it.rating / MAX_RATING) +
                RATING_COUNT_PARAMETER_WEIGHT * (it.ratingCount as Double / maxRatingCount) +
                CHECKINS_PARAMETER_WEIGHT * (it.checkins as Double / maxCheckins) +
                LIKES_PARAMETER_WEIGHT * (it.likes as Double / maxLikes)

        if (it.pictureImageUrl == null) superPubRating -= 0.1
        if (it.coverImageUrl == null) superPubRating -= 0.4
        if (it.phone == null) superPubRating -= 0.2

        // a. Rating count of each pub can not be < 15.
        // b. "SuperPubRating" must not to be < 1.
        // c. Pub must meet the minimum requirements for to have super pub rating.
        if (it.ratingCount < MINIMUM_RATING_COUNT_ALLOWED ||
                superPubRating < MINIMUM_SUPER_PUB_RATING_ALLOWED ||
                !it.hasMinimumRequirement) {

            it.superPubRating = 0.0

        } else {

            it.superPubRating = superPubRating
        }
    }

    // endregion

    // region 3. THE SUPER PUB RATING OF THE BEST EVALUATED PUB WILL BE ADJUSTED SO THAT IT IS NOT OVERRATED.

    if (size > 1) {

        Collections.sort(this)

        val highestSuperPubRatingPub = this[0]
        val secondHighestSuperPubRatingPub = this[1]

        // There should be more than one pub to adjust the "Super Pub Rating" highest pub.
        if (highestSuperPubRatingPub.ratingCount < 5000 ||
                highestSuperPubRatingPub.checkins < 80000 ||
                highestSuperPubRatingPub.likes < 40000 ||
                !(secondHighestSuperPubRatingPub.ratingCount < 5000 ||
                        secondHighestSuperPubRatingPub.checkins < 80000 ||
                        secondHighestSuperPubRatingPub.likes < 40000)) {

            val DIFFERENCE_BETWEEN_SUPER_PUB_RATINGS = Math.abs(
                    highestSuperPubRatingPub.superPubRating - secondHighestSuperPubRatingPub.superPubRating
            )

            val FIFTH_PER_CENT_FROM_SUPER_PUB_RATING = 0.7 * DIFFERENCE_BETWEEN_SUPER_PUB_RATINGS

            val NEWEST_SUPER_PUB_RATING = highestSuperPubRatingPub.superPubRating - FIFTH_PER_CENT_FROM_SUPER_PUB_RATING

            highestSuperPubRatingPub.superPubRating = NEWEST_SUPER_PUB_RATING
        }
    }

    // endregion
}

// endregion