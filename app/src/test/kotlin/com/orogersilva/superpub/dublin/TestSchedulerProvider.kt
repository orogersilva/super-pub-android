package com.orogersilva.superpub.dublin

import com.orogersilva.superpub.dublin.scheduler.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Created by orogersilva on 5/21/2017.
 */
class TestSchedulerProvider : SchedulerProvider {

    // region OVERRIDED METHODS

    override fun newThread(): Scheduler = Schedulers.trampoline()

    override fun ui(): Scheduler = Schedulers.trampoline()

    override fun io(): Scheduler = Schedulers.trampoline()

    // endregion
}