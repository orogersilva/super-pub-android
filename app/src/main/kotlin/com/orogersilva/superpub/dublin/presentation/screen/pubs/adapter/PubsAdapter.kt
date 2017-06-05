package com.orogersilva.superpub.dublin.presentation.screen.pubs.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orogersilva.superpub.dublin.R
import com.orogersilva.superpub.dublin.domain.model.Pub
import com.orogersilva.superpub.dublin.presentation.screen.ItemView

/**
 * Created by orogersilva on 4/21/2017.
 */
class PubsAdapter(private val pubs: MutableList<Pub>,
                  private val pubItemListener: PubItemListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // region PUBLIC METHODS

    fun clearData() {

        pubs.clear()

        notifyDataSetChanged()
    }

    fun replaceData(pubs: List<Pub>) {

        this.pubs.clear()
        this.pubs.addAll(pubs)

        notifyDataSetChanged()
    }

    // endregion

    // region OVERRIDED METHODS

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.cardview_pub, parent, false)

        v.tag = PubItemPresenter()

        return ItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        (holder?.itemView?.tag as PubItemPresenter).presentListItem(holder as ItemViewHolder, pubs[position])
    }

    override fun getItemCount() = pubs.size

    // endregion

    // region INTERFACES

    interface PubItemListener {

        // region METHODS

        fun onPubPressed(pub: Pub)

        // endregion
    }

    // endregion

    // region INNER CLASSES

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ItemView<Pub> {

        // region INITIALIZER BLOCK

        init {


        }

        // endregion

        // region OVERRIDED METHODS

        override fun setItem(item: Pub) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        // endregion
    }

    // endregion
}