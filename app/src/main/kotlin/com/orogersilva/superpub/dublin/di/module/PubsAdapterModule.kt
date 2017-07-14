package com.orogersilva.superpub.dublin.di.module

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.presentation.model.PubModel
import com.orogersilva.superpub.dublin.presentation.screen.pubdetails.view.PubDetailsActivity
import com.orogersilva.superpub.dublin.presentation.screen.pubs.PubsContract
import com.orogersilva.superpub.dublin.presentation.screen.pubs.adapter.PubsAdapter
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 6/12/2017.
 */
@ActivityScope
@Module
open class PubsAdapterModule() {

    // region PROVIDERS

    @Provides @ActivityScope open fun providePubsMutableList(): MutableList<PubModel> = mutableListOf<PubModel>()

    @Provides @ActivityScope open fun providePubItemListener(pubsView: PubsContract.View): PubsAdapter.PubItemListener =
            object : PubsAdapter.PubItemListener {

            override fun onPubPressed(pub: PubModel) {

                val sourceActivity = pubsView as AppCompatActivity

                val pubIntent = Intent(sourceActivity, PubDetailsActivity::class.java)

                pubIntent.putExtra("pub_extra", pub)

                sourceActivity.startActivity(pubIntent)
            }
        }

    @Provides @ActivityScope open fun providePubsAdapter(pubs: MutableList<PubModel>, pubItemListener: PubsAdapter.PubItemListener) =
            PubsAdapter(pubs, pubItemListener)

    @Provides @ActivityScope open fun providePubsLayoutManager(context: Context): RecyclerView.LayoutManager = LinearLayoutManager(context)

    // endregion
}