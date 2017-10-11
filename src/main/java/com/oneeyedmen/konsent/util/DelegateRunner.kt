package com.oneeyedmen.konsent.util

import org.junit.runner.Description
import org.junit.runner.Runner
import org.junit.runner.notification.RunNotifier

open class DelegateRunner(private val delegate: Runner) : Runner() {

    override fun getDescription(): Description = delegate.description

    override fun run(notifier: RunNotifier) = delegate.run(notifier)

}