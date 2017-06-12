package com.orogersilva.superpub.dublin.data.shared

import java.util.*

/**
 * Created by orogersilva on 5/28/2017.
 */

// region MUTABLE MAP EXTENSION METHODS

fun <K, V> MutableMap<K, V>?.toImmutableMap() = HashMap(this)

// endregion