package com.oneeyedmen.konsent.okeydoke

import com.oneeyedmen.konsent.FeatureRecorder
import com.oneeyedmen.okeydoke.Transcript

class TranscriptFeatureRecorder(val transcript: Transcript, val indent: Int = 4) : FeatureRecorder {

    private var lastTermName: String? = null

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

    override fun term(termName: String?, vararg items: Any) {
        indent(2).appendLine(collectTerms(termName, *items).joinToString(" "))
        lastTermName = termName
    }

    private fun indent(level: Int): Transcript {
        if (transcript.isStartOfLine)
            transcript.space(indent * level)
        return transcript
    }

    private fun collectTerms(termName: String?, vararg items: Any) : List<Any> =
        if (termName == null) items.toList()
        else listOf(termNameOrAnd(termName)).plus(items)

    private fun termNameOrAnd(termName: String) = if (termName == lastTermName) "and" else termName
}