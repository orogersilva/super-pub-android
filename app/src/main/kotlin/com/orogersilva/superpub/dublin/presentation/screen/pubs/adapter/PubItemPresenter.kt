package com.orogersilva.superpub.dublin.presentation.screen.pubs.adapter

import com.orogersilva.superpub.dublin.model.Pub

/**
 * Created by orogersilva on 4/21/2017.
 */
class PubItemPresenter {

    // region PUBLIC METHODS

    fun presentListItem(itemViewHolder: PubsAdapter.ItemViewHolder, pub: Pub) {

        itemViewHolder.setItem(pub)
    }

    // endregion
}