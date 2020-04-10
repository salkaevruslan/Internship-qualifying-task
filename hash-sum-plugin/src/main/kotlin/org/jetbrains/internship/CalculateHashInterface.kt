package org.jetbrains.internship

import org.gradle.api.provider.Property
import org.gradle.workers.WorkParameters

interface CalculateHashInterface : WorkParameters {
    val dir: Property<String>
    val hash: Property<String>
}