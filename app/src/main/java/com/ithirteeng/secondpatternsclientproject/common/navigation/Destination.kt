package com.ithirteeng.secondpatternsclientproject.common.navigation

abstract class Destination {

    private val destination = javaClass.simpleName

    val route: String by lazy {
        buildString {
            append(destination)
            arguments.forEach { arg ->
                append("/{$arg}")
            }
        }
    }

    var arguments: List<String> = emptyList()

    fun destinationWithArguments(vararg arguments: Any?) = buildString {
        append(destination)
        arguments.forEach { argument ->
            append("/$argument")
        }
    }

}