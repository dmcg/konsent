package com.oneeyedmen.konsent

@Suppress("UNCHECKED_CAST")
fun preamble(vararg strings: String) = Preamble(strings as Array<String>)

class Preamble(val strings: Array<String>)
