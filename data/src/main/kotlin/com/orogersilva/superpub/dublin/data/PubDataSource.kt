package com.orogersilva.superpub.dublin.data

import com.orogersilva.superpub.dublin.data.entity.PubEntity
import io.reactivex.Observable

/**
 * Created by orogersilva on 5/28/2017.
 */
interface PubDataSource {

    // region CRUD

    fun getPubs(query: String = "\"pub\"",
                type: String = "place",
                fromLocation: String,
                radius: Int = 5000,
                limit: Int = 100,
                fields: String = "location,name,overall_star_rating,rating_count,checkins,phone,fan_count,picture,cover",
                displayedDataTimestamp: Long = 0L): Observable<List<PubEntity>>?

    fun savePubs(pubs: List<PubEntity>?)

    fun deletePubs()

    // endregion
}