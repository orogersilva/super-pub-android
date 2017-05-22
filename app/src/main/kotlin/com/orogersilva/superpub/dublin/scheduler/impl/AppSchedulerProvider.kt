package com.orogersilva.superpub.dublin.scheduler.impl

import com.orogersilva.superpub.dublin.scheduler.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by orogersilva on 5/21/2017.
 */
class AppSchedulerProvider : SchedulerProvider {

    // region OVERRIDED METHODS

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun io(): Scheduler = Schedulers.io()

    // endregion
}