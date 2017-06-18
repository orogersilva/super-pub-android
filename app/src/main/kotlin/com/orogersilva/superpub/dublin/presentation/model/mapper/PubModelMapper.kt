package com.orogersilva.superpub.dublin.presentation.model.mapper

import com.orogersilva.superpub.dublin.domain.model.Pub
import com.orogersilva.superpub.dublin.presentation.model.PubModel

/**
 * Created by orogersilva on 6/12/2017.
 */
object PubModelMapper {

    // region METHODS

    fun transform(pub: Pub): PubModel {

        val pubModel = PubModel(pub.id,
                pub.name,
                pub.phone,
                pub.rating,
                pub.ratingCount,
                pub.checkins,
                pub.likes,
                pub.coverImageUrl,
                pub.pictureImageUrl,
                pub.latitude,
                pub.longitude,
                pub.street,
                pub.hasMinimumRequirement,
                pub.timestamp)

        pubModel.superPubRating = pub.superPubRating

        return pubModel
    }

    fun transformPubList(pubs: List<Pub>): List<PubModel> {

        val pubsModel = mutableListOf<PubModel>()

        pubs.forEach {
            pubsModel.add(transform(it))
        }

        return pubsModel
    }

    // endregion
}