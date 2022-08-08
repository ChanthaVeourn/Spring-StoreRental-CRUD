package com.example.storeRental

import java.util.Locale

fun String.toSlug(): String {
    return lowercase(Locale.getDefault()).
            replace("[^a-z\\d\\s]".toRegex(), " ").
            split(" ").
            joinToString ( "-").
            replace("-+".toRegex(), "-")
}