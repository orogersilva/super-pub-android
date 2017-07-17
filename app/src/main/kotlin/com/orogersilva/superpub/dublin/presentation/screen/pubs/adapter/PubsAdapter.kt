package com.orogersilva.superpub.dublin.presentation.screen.pubs.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.orogersilva.superpub.dublin.R
import com.orogersilva.superpub.dublin.domain.Rank
import com.orogersilva.superpub.dublin.presentation.model.PubModel
import com.orogersilva.superpub.dublin.presentation.screen.ItemView
import kotlinx.android.synthetic.main.cardview_pub.view.*
import java.text.DecimalFormat

/**
 * Created by orogersilva on 4/21/2017.
 */
class PubsAdapter(private val pubs: MutableList<PubModel>,
                  private val pubItemListener: PubItemListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // region PUBLIC METHODS

    /*fun clearData() {

        pubs.clear()

        notifyDataSetChanged()
    }*/

    fun replaceData(pubs: List<PubModel>) {

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

        fun onPubPressed(pub: PubModel)

        // endregion
    }

    // endregion

    // region INNER CLASSES

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ItemView<PubModel> {

        // region INITIALIZER BLOCK

        init {

            itemView.pubCardView.setOnClickListener {

                pubItemListener.onPubPressed(pubs[adapterPosition])
            }
        }

        // endregion

        // region OVERRIDED METHODS

        override fun setItem(item: PubModel) {

            if (item.pictureImageUrl != null) {

                Glide.with(itemView.context)
                        .load(item.pictureImageUrl)
                        .into(itemView.pubCircleImageView)

            } else {

                Glide.with(itemView.context)
                        .load(R.mipmap.ic_launcher)
                        .into(itemView.pubCircleImageView)
            }

            itemView.nameTextView.text = item.name

            when (item.rank) {

                Rank.GOLD -> itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.gold))
                Rank.SILVER -> itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.silver))
                else -> itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
            }

            val decimalFormatter = DecimalFormat("#.00")

            if (item.superPubRating == 0.0) {
                itemView.ratingTextView.text = itemView.context.getString(R.string.no_rating)
            } else {
                itemView.ratingTextView.text = decimalFormatter.format(item.superPubRating)
            }
        }

        // endregion
    }

    // endregion
}