package org.jetbrains.internship

import org.gradle.api.Plugin
import org.gradle.api.Project

class HashSumPlugin : Plugin<Project> {
    override fun apply(p0: Project) {
        p0.tasks.register("calculateSha1", CalculateHashTask::class.java) {
            hashType = "SHA-1"
        }
        p0.tasks.register("calculateMd2", CalculateHashTask::class.java) {
            hashType = "MD2"
        }
        p0.tasks.register("calculateMd5", CalculateHashTask::class.java) {
            hashType = "MD5"
        }
        p0.tasks.register("calculateSha224", CalculateHashTask::class.java) {
            hashType = "SHA-224"
        }
        p0.tasks.register("calculateSha256", CalculateHashTask::class.java) {
            hashType = "SHA-256"
        }
        p0.tasks.register("calculateSha384", CalculateHashTask::class.java) {
            hashType = "SHA-384"
        }
        p0.tasks.register("calculateSha512", CalculateHashTask::class.java) {
            hashType = "SHA-512"
        }
    }
}
