package org.jetbrains.internship

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File
import org.gradle.workers.WorkerExecutor
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

open class CalculateHashTask @Inject constructor(private val workerExecutor: WorkerExecutor) : DefaultTask() {
    @get:Input
    var hashType: String = ""


    @TaskAction
    fun calculateHashTask() {
        val parentDir = project.rootDir.absolutePath
        val projectDirs: List<String> = findDirs(parentDir, hashType)
        val workQueue = workerExecutor.noIsolation()
        projectDirs.forEach {
            workQueue.submit(CalculateHash::class.java) {
                dir.set(parentDir)
                hash.set(hashType)
            }
        }
    }

    private fun findDirs(dir: String, hashType: String): List<String> {
        val projectDirs: ArrayList<String> = ArrayList()
        val fileName = "hash${getHashFileExtension(hashType)}"
        val extensionsArray = arrayListOf("kt", "java")
        val stack = Stack<String>()
        File(dir).walkTopDown().forEach { currentPath ->
            //выходим из подпроектов
            while (!stack.empty() && !currentPath.absolutePath.startsWith(stack.peek())) {
                stack.pop()
            }
            // Проверяем зашли ли мы в очередной подпроект и если в нем уже собран файл
            // отправляем на последующую проверку для инкрементальной сборки
            if (File("${currentPath.absolutePath}\\build").exists()) {
                stack.push(currentPath.absolutePath)
                if (!File("${currentPath.absolutePath}\\build\\$fileName").exists())
                    projectDirs.add(currentPath.absolutePath)
            }
            //проверяем для очередного файла был ли он ранее неизменным обработан в подпроектах
            if (extensionsArray.contains(currentPath.extension)) {
                stack.forEach {
                    val path = currentPath.absolutePath
                    val fileHashPath = path.removePrefix("$it\\").removeSuffix(currentPath.name)
                    val hashFile = File(
                        "$it\\build\\fileHashes\\${fileHashPath}" +
                                "${currentPath.nameWithoutExtension}${getHashFileExtension(hashType)}"
                    )
                    if (hashFile.exists()) {
                        if (!checkHash(hashFile, File(currentPath.absolutePath), hashType)) {
                            projectDirs.add(it)
                        }
                    } else {
                        projectDirs.add(it)
                    }
                }
            }
        }
        //тк некоторые проекты мы могли добавить несколько раз
        return projectDirs.distinct()
    }

}