package com.orogersilva.superpub.dublin.presentation.screen.login.view

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.nhaarman.mockito_kotlin.mock
import com.orogersilva.superpub.dublin.CustomInstrumentationTestApplication
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

    private lateinit var loginManagerMock: LoginManager
    private lateinit var callbackManagerMock: CallbackManager
    private lateinit var accessTokenMock: String

    @Rule @JvmField val activityRule = ActivityTestRule(LoginActivity::class.java, true, false)

    // endregion

    // region SETUP METHOD

    @Before fun setup() {

        loginManagerMock = mock()
        callbackManagerMock = mock()
        accessTokenMock = mock()

        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        application = InstrumentationRegistry.getTargetContext().applicationContext as CustomInstrumentationTestApplication

        application.createApplicationComponent(loginManagerMock, callbackManagerMock, accessTokenMock)

        activityRule.launchActivity(Intent())
    }

    // endregion

    // region TEST METHODS

    @Test fun shouldDisplayPermissionRequestDialogAtStartup() {

        assertViewWithTextIsVisible(device, "DENY")
        assertViewWithTextIsVisible(device, "ALLOW")

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

        val denyButton = device.findObject(UiSelector().text("DENY"))

        denyButton.click()
    }

    // endregion
}