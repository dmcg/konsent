package com.oneeyedmen.konsent

interface Selector<DriverT, ResultT> {
    fun select(actor: Actor<DriverT>): ResultT
    val description: String
}

fun <DriverT, ResultT> selector(description: String, block: Actor<DriverT>.() -> ResultT) =
    object: Selector<DriverT, ResultT> {
        override val description = description
        override fun select(actor: Actor<DriverT>) = block(actor)
    }