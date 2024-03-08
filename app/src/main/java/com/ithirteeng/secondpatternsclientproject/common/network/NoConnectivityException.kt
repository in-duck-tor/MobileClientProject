package com.ithirteeng.secondpatternsclientproject.common.network

import java.io.IOException

class NoConnectivityException : IOException() {

    override val message: String
        get() = "No Internet Connection"
}
