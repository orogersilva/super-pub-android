package com.orogersilva.superpub.dublin.scheduler

import io.reactivex.Scheduler

/**
 * Created by orogersilva on 5/21/2017.
 */
interface SchedulerProvider {

    // region METHODS

    fun newThread(): Scheduler

    fun ui(): Scheduler

    fun io(): Scheduler

    // endregion
}