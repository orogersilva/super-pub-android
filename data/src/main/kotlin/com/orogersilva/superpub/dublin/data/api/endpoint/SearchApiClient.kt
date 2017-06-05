package com.orogersilva.superpub.dublin.data.api.endpoint

import com.orogersilva.superpub.dublin.data.entity.PubHttpResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by orogersilva on 5/28/2017.
 */
interface SearchApiClient {

    // region ENDPOINTS

    @GET("search")
    fun getPubs(@Query("q") query: String,
                @Query("type") type: String,
                @Query("center") locationStr: String,
                @Query("distance") distance: Int,
                @Query("limit") radius: Int,
                @Query("fields") fields: String,
                @Query("access_token") accessToken: String) : Observable<PubHttpResponse>

    // endregion
}