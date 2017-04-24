package com.orogersilva.superpub.dublin.api.endpoint

import com.orogersilva.superpub.dublin.model.PubHttpResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by orogersilva on 4/21/2017.
 */
interface SearchingApiClient {

    // region ENDPOINTS

    @GET("search")
    fun getPubs(@Query("q") query: String,
                @Query("type") type: String,
                @Query("center") locationStr: String,
                @Query("distance") distance: Int,
                @Query("limit") radius: Int,
                @Query("fields") fields: String) : Observable<PubHttpResponse>

    // endregion
}