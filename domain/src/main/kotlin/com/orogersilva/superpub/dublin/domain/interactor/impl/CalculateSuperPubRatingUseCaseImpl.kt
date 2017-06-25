package com.orogersilva.superpub.dublin.domain.interactor.impl

import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.domain.interactor.CalculateSuperPubRatingUseCase
import com.orogersilva.superpub.dublin.domain.model.Pub
import io.reactivex.Flowable
import java.util.*

/**
 * Created by orogersilva on 6/13/2017.
 */
@ActivityScope
class CalculateSuperPubRatingUseCaseImpl : CalculateSuperPubRatingUseCase {

    // region OVERRIDED METHODS

    override fun calculateSuperPubRating(pubs: List<Pub>): Flowable<Pub> {

        if (pubs.isEmpty()) return Flowable.empty()

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

        pubs.forEach {

            if (it.ratingCount > maxRatingCount) maxRatingCount = it.ratingCount
            if (it.checkins > maxCheckins) maxCheckins = it.checkins
            if (it.likes > maxLikes) maxLikes = it.likes
        }

        // endregion

        // region 2. CALCULATE "SUPER PUB" RATING.

        if (maxRatingCount == 0) maxRatingCount = 1
        if (maxCheckins == 0) maxCheckins = 1
        if (maxLikes == 0) maxLikes = 1

        pubs.forEach {

            var superPubRating = RATING_PARAMETER_WEIGHT * (it.rating / MAX_RATING) +
                    RATING_COUNT_PARAMETER_WEIGHT * (it.ratingCount.toDouble() / maxRatingCount) +
                    CHECKINS_PARAMETER_WEIGHT * (it.checkins.toDouble() / maxCheckins) +
                    LIKES_PARAMETER_WEIGHT * (it.likes.toDouble() / maxLikes)

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

        if (pubs.size > 1) {

            Collections.sort(pubs)

            val highestSuperPubRatingPub = pubs[0]
            val secondHighestSuperPubRatingPub = pubs[1]

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

        return Flowable.fromIterable(pubs)
    }

    // endregion
}