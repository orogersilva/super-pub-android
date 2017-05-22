package com.orogersilva.superpub.dublin

import com.orogersilva.superpub.dublin.scheduler.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import javax.inject.Inject

/**
 * Created by orogersilva on 5/21/2017.
 */
class TestSchedulerProvider : SchedulerProvider {

    // region OVERRIDED METHODS

    override fun ui(): Scheduler = Schedulers.trampoline()

    override fun io(): Scheduler = Schedulers.trampoline()

    // endregion
}