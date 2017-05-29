package com.orogersilva.superpub.dublin.data.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by orogersilva on 5/28/2017.
 */
object RestClient {

    // region PROPERTIES

    private lateinit var retrofit: Retrofit

    // endregion

    // region PUBLIC METHODS

    fun<T> getApiClient(serviceClass: Class<T>, baseEndpoint: String): T {

        retrofit = Retrofit.Builder()
                .baseUrl(baseEndpoint)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(serviceClass)
    }

    // endregion
}