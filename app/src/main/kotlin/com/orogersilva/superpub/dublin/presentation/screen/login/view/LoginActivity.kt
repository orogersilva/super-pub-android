package com.orogersilva.superpub.dublin.presentation.screen.login.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.orogersilva.superpub.dublin.R
import com.orogersilva.superpub.dublin.presentation.screen.login.LoginContract
import kotlinx.android.synthetic.main.toolbar_main.*

/**
 * Created by orogersilva on 4/8/2017.
 */
class LoginActivity : AppCompatActivity(), LoginContract.View {

    // region ACTIVITY LIFECYCLE METHODS

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toolbar.title = ""
    }

    // endregion

    // region OVERRIDED METHODS

    override fun setPresenter(presenter: LoginContract.Presenter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // endregion
}