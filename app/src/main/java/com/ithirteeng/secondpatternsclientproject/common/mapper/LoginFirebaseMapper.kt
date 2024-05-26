package com.ithirteeng.secondpatternsclientproject.common.mapper

fun String.toFirebaseLogin(): String = this
    .replace("%", "%25")
    .replace(".", "%2E")
    .replace("#", "%23")
    .replace("$", "%24")
    .replace("/", "%2F")
    .replace("[", "%5B")
    .replace("]", "%5D")
