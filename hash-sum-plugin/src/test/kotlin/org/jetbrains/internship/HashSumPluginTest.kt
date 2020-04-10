package org.jetbrains.internship

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File

class HashSumPluginTest {
    companion object {
        val FILE_PATH_SHA_1: String
            get() = "hash.sha1"
        val FILE_PATH_MD_2: String
            get() = "hash.md2"
        val FILE_PATH_MD_5: String
            get() = "hash.md5"
        val FILE_PATH_SHA_224: String
            get() = "hash.sha224"
        val FILE_PATH_SHA_256: String
            get() = "hash.sha256"
        val FILE_PATH_SHA_384: String
            get() = "hash.sha384"
        val FILE_PATH_SHA_512: String
            get() = "hash.sha512"
        const val CALCULATE_SHA_1_TASK_NAME = "calculateSha1"
        const val CALCULATE_MD_2_TASK_NAME = "calculateMd2"
        const val CALCULATE_MD_5_TASK_NAME = "calculateMd5"
        const val CALCULATE_SHA_224_TASK_NAME = "calculateSha224"
        const val CALCULATE_SHA_256_TASK_NAME = "calculateSha256"
        const val CALCULATE_SHA_384_TASK_NAME = "calculateSha384"
        const val CALCULATE_SHA_512_TASK_NAME = "calculateSha512"

        val String.isSHA1Content: Boolean
            get() = matches("^[a-fA-F0-9]{40}$".toRegex())

        val String.isMD2Content: Boolean
            get() = matches("^[a-fA-F0-9]{32}$".toRegex())

        val String.isMD5Content: Boolean
            get() = matches("^[a-fA-F0-9]{32}$".toRegex())

        val String.isSHA224Content: Boolean
            get() = matches("^[a-fA-F0-9]{56}$".toRegex())

        val String.isSHA256Content: Boolean
            get() = matches("^[a-fA-F0-9]{64}$".toRegex())

        val String.isSHA384Content: Boolean
            get() = matches("^[a-fA-F0-9]{96}$".toRegex())

        val String.isSHA512Content: Boolean
            get() = matches("^[a-fA-F0-9]{128}$".toRegex())

    }

    private val exampleProjectDir: File =
        File(System.getProperty("user.dir")).parentFile//.resolve("example-project")

    private val project: Project = ProjectBuilder.builder().withProjectDir(exampleProjectDir).build()

    @Before
    fun setUp() {
        project.plugins.apply(HashSumPlugin::class.java)
    }

    @Test
    fun basicTest() {
        with(project.plugins) {
            assertTrue(hasPlugin(HashSumPlugin::class.java))
        }
    }

    @Test
    fun taskSha1Test() {
        val calculateSha1Task = project.tasks.findByPath(CALCULATE_SHA_1_TASK_NAME)
        assertNotNull(calculateSha1Task)
        calculateSha1Task!!.actions.forEach {
            it.execute(calculateSha1Task)
        }
        val hashSumFile = project.buildDir.resolve(FILE_PATH_SHA_1)
        assertTrue(hashSumFile.exists() && hashSumFile.readText().isSHA1Content)
    }

    @Test
    fun taskMd2Test() {
        val calculateMd2Task = project.tasks.findByPath(CALCULATE_MD_2_TASK_NAME)
        assertNotNull(calculateMd2Task)
        calculateMd2Task!!.actions.forEach {
            it.execute(calculateMd2Task)
        }
        val hashSumFile = project.buildDir.resolve(FILE_PATH_MD_2)
        assertTrue(hashSumFile.exists() && hashSumFile.readText().isMD2Content)
    }

    @Test
    fun taskMd5Test() {
        val calculateMd5Task = project.tasks.findByPath(CALCULATE_MD_5_TASK_NAME)
        assertNotNull(calculateMd5Task)
        calculateMd5Task!!.actions.forEach {
            it.execute(calculateMd5Task)
        }
        val hashSumFile = project.buildDir.resolve(FILE_PATH_MD_5)
        assertTrue(hashSumFile.exists() && hashSumFile.readText().isMD5Content)
    }

    @Test
    fun taskSha224Test() {
        val calculateSha224Task = project.tasks.findByPath(CALCULATE_SHA_224_TASK_NAME)
        assertNotNull(calculateSha224Task)
        calculateSha224Task!!.actions.forEach {
            it.execute(calculateSha224Task)
        }
        val hashSumFile = project.buildDir.resolve(FILE_PATH_SHA_224)
        assertTrue(hashSumFile.exists() && hashSumFile.readText().isSHA224Content)
    }

    @Test
    fun taskSha256Test() {
        val calculateSha256Task = project.tasks.findByPath(CALCULATE_SHA_256_TASK_NAME)
        assertNotNull(calculateSha256Task)
        calculateSha256Task!!.actions.forEach {
            it.execute(calculateSha256Task)
        }
        val hashSumFile = project.buildDir.resolve(FILE_PATH_SHA_256)
        assertTrue(hashSumFile.exists() && hashSumFile.readText().isSHA256Content)
    }

    @Test
    fun taskSha384Test() {
        val calculateSha384Task = project.tasks.findByPath(CALCULATE_SHA_384_TASK_NAME)
        assertNotNull(calculateSha384Task)
        calculateSha384Task!!.actions.forEach {
            it.execute(calculateSha384Task)
        }
        val hashSumFile = project.buildDir.resolve(FILE_PATH_SHA_384)
        assertTrue(hashSumFile.exists() && hashSumFile.readText().isSHA384Content)
    }

    @Test
    fun taskSha512Test() {
        val calculateSha512Task = project.tasks.findByPath(CALCULATE_SHA_512_TASK_NAME)
        assertNotNull(calculateSha512Task)
        calculateSha512Task!!.actions.forEach {
            it.execute(calculateSha512Task)
        }
        val hashSumFile = project.buildDir.resolve(FILE_PATH_SHA_512)
        assertTrue(hashSumFile.exists() && hashSumFile.readText().isSHA512Content)
    }
}

