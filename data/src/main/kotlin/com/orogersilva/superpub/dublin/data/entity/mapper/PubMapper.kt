package com.orogersilva.superpub.dublin.data.entity.mapper

import com.orogersilva.superpub.dublin.data.entity.PubEntity
import com.orogersilva.superpub.dublin.data.entity.PubHttpResponse
import com.orogersilva.superpub.dublin.domain.model.Pub

/**
 * Created by orogersilva on 5/28/2017.
 */
object PubMapper {

    // region METHODS

    fun transform(pubEntity: PubEntity): Pub =
            Pub(pubEntity.id,
                    pubEntity.name,
                    pubEntity.phone,
                    pubEntity.rating,
                    pubEntity.ratingCount,
                    pubEntity.checkins,
                    pubEntity.likes,
                    pubEntity.coverImageUrl,
                    pubEntity.pictureImageUrl,
                    pubEntity.latitude,
                    pubEntity.longitude,
                    pubEntity.street,
                    pubEntity.hasMinimumRequirement,
                    pubEntity.timestamp)

    fun transform(pubData: PubHttpResponse.PubData): PubEntity =
            PubEntity(pubData.id,
                    pubData.name,
                    pubData.phone,
                    pubData.rating,
                    pubData.ratingCount,
                    pubData.checkins,
                    pubData.fanCount,
                    pubData.cover?.source,
                    pubData.picture.picturedata.url,
                    pubData.location.latitude,
                    pubData.location.longitude,
                    pubData.location.street,
                    !pubData.picture.picturedata.isSilhouette,
                    0L)

    fun transformPubEntityList(pubsEntity: List<PubEntity>): List<Pub> {

        val pubs = mutableListOf<Pub>()

        pubsEntity.forEach {
            pubs.add(transform(it))
        }

        return pubs
    }

    fun transformPubsDataList(pubsData: List<PubHttpResponse.PubData>): List<PubEntity> {

        val pubs = mutableListOf<PubEntity>()

        pubsData.forEach {
            pubs.add(transform(it))
        }

        return pubs
    }

    // endregion
}