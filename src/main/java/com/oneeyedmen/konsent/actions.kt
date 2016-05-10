package com.oneeyedmen.konsent

import kotlin.reflect.KFunction

interface Action {
    operator fun invoke(actor: Actor<*>)
    val description: String
}

fun Action(description: String, block: (Actor<*>) -> Unit) = object: Action {
    override val description = description

    override operator fun invoke(actor: Actor<*>) = block(actor)
}

fun asAction(action: (Actor<*>) -> Unit) = object: Action {
    override fun invoke(actor: Actor<*>) = action(actor)
    override val description = (action as KFunction<*>).name
}
