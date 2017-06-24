package com.orogersilva.superpub.dublin.presentation.screen.login.view

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.orogersilva.superpub.dublin.R
import com.orogersilva.superpub.dublin.di.component.LoginActivityComponent
import com.orogersilva.superpub.dublin.di.module.FacebookAdapterServiceModule
import com.orogersilva.superpub.dublin.di.module.LoginPresenterModule
import com.orogersilva.superpub.dublin.presentation.screen.login.LoginContract
import com.orogersilva.superpub.dublin.presentation.screen.pubs.view.PubsActivity
import com.orogersilva.superpub.dublin.shared.app
import com.orogersilva.superpub.dublin.shared.hasPermission
import com.orogersilva.superpub.dublin.shared.intentFor
import com.orogersilva.superpub.dublin.shared.permissionsHasBeenGranted
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

/**
 * Created by orogersilva on 4/8/2017.
 */
class LoginActivity : AppCompatActivity(), LoginContract.View {

    // region PROPERTIES

    @Inject lateinit var loginPresenter: LoginContract.Presenter

    private var loginActivityComponent: LoginActivityComponent? = null


    private val ACCESS_STORAGE_PERMISSION_REQUEST_CODE = 1

    private var hasPermissionToAccessDeviceStorage = false

    // endregion

    // region ACTIVITY LIFECYCLE METHODS

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        customToolbar.title = ""

        setSupportActionBar(customToolbar)

        loginActivityComponent = app().applicationComponent
                .newLoggedOutComponent()
                .newLoginActivityComponent(FacebookAdapterServiceModule(), LoginPresenterModule(this))

        loginActivityComponent?.inject(this)

        loginRippleView.setOnRippleCompleteListener { loginPresenter.login() }

        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) &&
                !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // TODO: SHOULD BE IMPLEMENTED EXPLANATION TO THE USE ABOUT WHY TO USE LOCATION FROM DEVICE.

            } else {

                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        ACCESS_STORAGE_PERMISSION_REQUEST_CODE)
            }

            return
        }
    }

    override fun onResume() {

        super.onResume()

        if (hasPermissionToAccessDeviceStorage) loginPresenter.resume()
    }

    override fun onDestroy() {

        super.onDestroy()

        loginActivityComponent = null
    }

    // endregion

    // region OVERRIDED METHODS

    override fun setPresenter(presenter: LoginContract.Presenter) {

        loginPresenter = presenter
    }

    override fun goToPubsScreen() {

        startActivity(intentFor(PubsActivity::class.java))

        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        loginPresenter.onResultFromFacebookService(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {

            ACCESS_STORAGE_PERMISSION_REQUEST_CODE -> {

                if (permissionsHasBeenGranted(grantResults)) {

                    loginPresenter.resume()

                } else {

                    // TODO: Denied permission. To implement.
                }
            }
        }
    }

    // endregion
}