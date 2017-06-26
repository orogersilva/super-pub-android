package com.orogersilva.superpub.dublin.presentation.screen.login.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.orogersilva.superpub.dublin.R
import com.orogersilva.superpub.dublin.di.component.LoginActivityComponent
import com.orogersilva.superpub.dublin.di.module.FacebookAdapterServiceModule
import com.orogersilva.superpub.dublin.di.module.LoginPresenterModule
import com.orogersilva.superpub.dublin.presentation.screen.login.LoginContract
import com.orogersilva.superpub.dublin.presentation.screen.pubs.view.PubsActivity
import com.orogersilva.superpub.dublin.shared.app
import com.orogersilva.superpub.dublin.shared.intentFor
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

/**
 * Created by orogersilva on 6/26/2017.
 */
class LoginActivity : AppCompatActivity(), LoginContract.View {

    // region PROPERTIES

    @Inject lateinit var loginPresenter: LoginContract.Presenter

    private var loginActivityComponent: LoginActivityComponent? = null

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
    }

    override fun onResume() {

        super.onResume()

        loginPresenter.resume()
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

    // endregion
}