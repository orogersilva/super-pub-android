package com.orogersilva.superpub.dublin.data

import com.orogersilva.superpub.dublin.model.Pub
import io.reactivex.Observable
import io.reactivex.schedulers.Timed

/**
 * Created by orogersilva on 3/31/2017.
 */
interface PubDataSource {

    // region CRUD

    fun getPubs(query: String = "pub",
                type: String = "place",
                center: String,
                radius: Int = 5000,
                limit: Int = 200,
                fields: String = "location,name,overall_star_rating,rating_count,checkins,phone,fan_count,picture,cover",
                displayedDataTimestamp: Long = 0L): Observable<Pub>?
    fun savePubs(pubs: List<Pub>, deleteExistingPubs: Boolean = false)

    // endregion
}