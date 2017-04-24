package com.orogersilva.superpub.dublin.api

import com.orogersilva.superpub.dublin.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by orogersilva on 4/21/2017.
 */
object RestClient {

    // region PROPERTIES

    private lateinit var retrofit: Retrofit

    // endregion

    // region PUBLIC METHODS

    fun<T> getApiClient(serviceClass: Class<T>, baseEndpoint: String = BuildConfig.FACEBOOK_GRAPH_API): T {

        retrofit = Retrofit.Builder()
                .baseUrl(baseEndpoint)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(serviceClass)
    }

    // endregion
}