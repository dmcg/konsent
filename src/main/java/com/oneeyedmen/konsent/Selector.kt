package com.oneeyedmen.konsent

interface Selector<ResultT, DriverT> {
    fun select(actor: Actor<DriverT>): ResultT
    val description: String
}

fun <ResultT, DriverT> selector(description: String, block: Actor<DriverT>.() -> ResultT) =
    object: Selector<ResultT, DriverT> {
        override val description = description
        override fun select(actor: Actor<DriverT>) = block(actor)
    }