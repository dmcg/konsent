package com.oneeyedmen.konsent.okeydoke

import com.oneeyedmen.konsent.Actor
import com.oneeyedmen.konsent.FeatureRecorder
import com.oneeyedmen.okeydoke.Transcript

class TranscriptFeatureRecorder(val transcript: Transcript, val indent: Int = 4) : FeatureRecorder {

    private var lastTermName: String? = null
    private var lastActor: Actor<*>? = null
    private var lastOperation: String? = null

    override fun featureStart(name: String, vararg preamble: String) {
        transcript.append("Feature: ").appendLine(name)
        preamble.forEach {
            indent(1).appendLine(it)
        }
    }

    override fun scenarioStart(name: String) {
        reset()
        transcript.endl()
        indent(1).append("Scenario: ").appendLine(name)
    }

    override fun term(termName: String?, actor: Actor<*>, operation: String, vararg items: Any) {
        indent(2).appendLine(collectTerms(termName, actor, operation, *items).joinToString(" "))
        lastActor = actor
        lastTermName = termName
        lastOperation = operation
    }


    private fun indent(level: Int): Transcript {
        if (transcript.isStartOfLine)
            transcript.space(indent * level)
        return transcript
    }

    private fun collectTerms(termName: String?, actor: Actor<*>, operation: String, vararg items: Any) : List<Any> =
        listOf(prefixOrAnd(termName.orEmpty(), actor, operation)).plus(items)

    private fun prefixOrAnd(termName: String, actor: Actor<*>, operation: String): String {
        val actorName = if (actor == lastActor) actor.pronoun else actor.name
        val termNameAndSeparator = if (termName.isEmpty()) "" else termName + " "
        return when {
            termName == lastTermName && actor == lastActor && operation == lastOperation-> "and"
            termName == lastTermName -> "and $actorName $operation"
            else -> "$termNameAndSeparator${actorName} $operation"
        }
    }

    private fun reset() {
        lastTermName = null
        lastActor = null
        lastOperation = null
    }
}