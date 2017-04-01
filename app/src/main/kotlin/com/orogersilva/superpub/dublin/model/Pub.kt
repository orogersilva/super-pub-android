package com.orogersilva.superpub.dublin.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by orogersilva on 3/31/2017.
 */
open class Pub(@PrimaryKey var id: Long = 0,
               var name: String = "",
               var phone: String? = null,
               var rating: Double = 0.0,
               var ratingCount: Int = 0,
               var checkins: Int = 0,
               var likes: Int = 0,
               var coverImageUrl: String? = null,
               var pictureImageUrl: String? = null,
               var latitude: Double = 0.0,
               var longitude: Double = 0.0,
               var street: String? = null,
               var hasMinimumRequirement: Boolean = false) : RealmObject(), Comparable<Pub> {

    // region OVERRIDED METHODS

    override fun compareTo(other: Pub): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // endregion
}