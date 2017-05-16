package com.orogersilva.superpub.dublin.presentation.screen.login.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.orogersilva.superpub.dublin.R
import com.orogersilva.superpub.dublin.di.components.LoginComponent
import com.orogersilva.superpub.dublin.di.modules.FacebookSdkModule
import com.orogersilva.superpub.dublin.di.modules.LoginPresenterModule
import com.orogersilva.superpub.dublin.presentation.screen.login.LoginContract
import com.orogersilva.superpub.dublin.presentation.screen.login.LoginPresenter
import com.orogersilva.superpub.dublin.presentation.screen.pubs.view.PubsActivity
import com.orogersilva.superpub.dublin.shared.app
import com.orogersilva.superpub.dublin.shared.intentFor
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar_main.*
import javax.inject.Inject

/**
 * Created by orogersilva on 4/8/2017.
 */
class LoginActivity : AppCompatActivity(), LoginContract.View {

    // region PROPERTIES

    @Inject lateinit var loginPresenter: LoginContract.Presenter

    private val loginComponent by lazy {
        app().applicationComponent.newLoginComponent(LoginPresenterModule(this), FacebookSdkModule())
    }

    // endregion

    // region ACTIVITY LIFECYCLE METHODS

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toolbar.title = ""

        setSupportActionBar(toolbar)

        loginComponent.inject(this)

        loginRippleView.setOnRippleCompleteListener { loginPresenter.login() }
    }

    override fun onResume() {

        super.onResume()

        loginPresenter.resume()
    }

    override fun onDestroy() {

        super.onDestroy()
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

        loginPresenter.onResultFromFacebookApi(requestCode, resultCode, data)
    }

    // endregion
}