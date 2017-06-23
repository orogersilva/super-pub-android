package com.orogersilva.superpub.dublin

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnitRunner
import com.linkedin.android.testbutler.TestButler

/**
 * Created by orogersilva on 6/20/2017.
 */
class CustomInstrumentationTestRunner : AndroidJUnitRunner() {

    // region OVERRIDED METHODS

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application =
            super.newApplication(cl, CustomInstrumentationTestApplication::class.qualifiedName, context)

    override fun onStart() {

        TestButler.setup(InstrumentationRegistry.getTargetContext())

        super.onStart()
    }

    override fun finish(resultCode: Int, results: Bundle?) {

        TestButler.teardown(InstrumentationRegistry.getTargetContext())

        super.finish(resultCode, results)
    }

    // endregion
}