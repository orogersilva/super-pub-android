package com.orogersilva.superpub.dublin.presentation.screen.login.view

import android.content.Intent
import android.content.pm.PackageManager
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import android.support.test.uiautomator.Until
import com.orogersilva.superpub.dublin.BuildConfig
import com.orogersilva.superpub.dublin.CustomInstrumentationTestApplication
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by orogersilva on 6/20/2017.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {

    // region PROPERTIES

    private lateinit var application: CustomInstrumentationTestApplication

    private lateinit var device: UiDevice

    private val APP_PACKAGE = BuildConfig.APPLICATION_ID
    private val LAUNCH_TIMEOUT = 5000L

    @Rule @JvmField val activityRule = ActivityTestRule(LoginActivity::class.java, true, false)

    // endregion

    // region SETUP METHOD

    @Before fun setup() {

        application = InstrumentationRegistry.getTargetContext().applicationContext as CustomInstrumentationTestApplication

        application.createApplicationComponent()

        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher...
        val launcherPackage = getLauncherPackageName()

        assertThat(launcherPackage, notNullValue())

        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT)

        activityRule.launchActivity(Intent())

        // Wait for the app to appear...
        device.wait(Until.hasObject(By.pkg(APP_PACKAGE).depth(0)), LAUNCH_TIMEOUT)
    }

    // endregion

    // region TEST METHODS

    @Test fun shouldDisplayPermissionRequestDialogAtStartup() {

        assertViewWithTextIsVisible(device, "Deny")
        assertViewWithTextIsVisible(device, "Allow")

        denyCurrentPermission(device)
    }

    // endregion

    // region UTILITY METHODS

    private fun assertViewWithTextIsVisible(device: UiDevice, text: String) {

        val allowButton = device.findObject(UiSelector().text(text))

        if (!allowButton.exists()) {
            throw AssertionError("View with text <$text> not found!")
        }
    }

    private @kotlin.jvm.Throws(android.support.test.uiautomator.UiObjectNotFoundException::class) fun denyCurrentPermission(device: UiDevice) {

        val denyButton = device.findObject(UiSelector().text("Deny"))

        denyButton.click()
    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private fun getLauncherPackageName(): String {

        // Create launcher Intent...
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        // Use PackageManager to get the launcher package name...
        val pm = InstrumentationRegistry.getContext().packageManager
        val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)

        return resolveInfo.activityInfo.packageName
    }

    // endregion
}