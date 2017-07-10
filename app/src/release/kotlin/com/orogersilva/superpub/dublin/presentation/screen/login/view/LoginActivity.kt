package com.orogersilva.superpub.dublin.presentation.screen.login.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
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

    override fun showLoginErrorMessage() {

        val snackBar = Snackbar.make(loginCoordinatorLayout,
                getString(R.string.login_error_message),
                Snackbar.LENGTH_LONG)

        val snackbarTextView = snackBar.view.findViewById(android.support.design.R.id.snackbar_text) as TextView
        snackbarTextView.setTextColor(ContextCompat.getColor(this, R.color.black))

        snackBar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.gold))

        snackBar.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        loginPresenter.onResultFromFacebookService(requestCode, resultCode, data)
    }

    // endregion
}