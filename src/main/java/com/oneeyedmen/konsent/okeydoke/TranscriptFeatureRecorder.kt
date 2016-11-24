package com.oneeyedmen.konsent.okeydoke

import com.oneeyedmen.konsent.Actor
import com.oneeyedmen.konsent.FeatureRecorder
import com.oneeyedmen.okeydoke.Transcript

class TranscriptFeatureRecorder(val transcript: Transcript, val indent: Int = 4) : FeatureRecorder {
    private var lastTermName: String? = null
    private var lastActor: Actor<*>? = null

    override fun featureStart(name: String, vararg preamble: String) {
        transcript.append("Feature: ").appendLine(name)
        preamble.forEach {
            indent(1).appendLine(it)
        }
    }

    override fun scenarioStart(name: String) {
        transcript.endl()
        indent(1).append("Scenario: ").appendLine(name)
    }

    override fun term(termName: String?, actor: Actor<*>, vararg items: Any) {
        indent(2).appendLine(collectTerms(termName, actor, *items).joinToString(" "))
        lastActor = actor
        lastTermName = termName
    }


    private fun indent(level: Int): Transcript {
        if (transcript.isStartOfLine)
            transcript.space(indent * level)
        return transcript
    }

    private fun collectTerms(termName: String?, actor: Actor<*>, vararg items: Any) : List<Any> =
        listOf(prefixOrAnd(termName.orEmpty(), actor)).plus(items)

    private fun prefixOrAnd(termName: String, actor: Actor<*>) =
        when {
            termName == lastTermName && actor == lastActor -> "and"
            termName == lastTermName -> "and ${actor.name}"
            termName.isBlank() -> actor.name
            else -> "$termName ${actor.name}"
        }
}