package org.jetbrains.internship

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.security.MessageDigest
import java.util.Stack


class HashSumPlugin : Plugin<Project> {
    override fun apply(p0: Project) {
        p0.tasks.create("calculateSha1") {
            doFirst {
                val dir = p0.rootDir.absolutePath
                val fileName = "hash.txt"
                val extensionsArray = arrayListOf("kt", "java")
                //Будем хранить stack digest-ов подпроектов в которых мы находимся
                val stack = Stack<Pair<MessageDigest, String>>()
                File(dir).walkTopDown().forEach { currentPath ->
                    //если мы вышли из подроекта, то записываем в его build директорию файл с нашим хешем
                    while (!stack.empty() && !currentPath.absolutePath.startsWith(stack.peek().second)) {
                        val file = File("${stack.peek().second}\\build\\$fileName")
                        file.writeText(stack.peek().first.digest().toHexString())
                        stack.pop()
                    }
                    // Проверяем зашли ли мы в очередной подпроект
                    if (File("${currentPath.absolutePath}\\build").exists()) {
                        stack.push(Pair(MessageDigest.getInstance("SHA-1"), currentPath.absolutePath))
                    }
                    if (extensionsArray.contains(currentPath.extension)) {
                        val textBytes = currentPath.readBytes()
                        stack.forEach {
                            it.first.update(textBytes, 0, textBytes.size)
                        }
                    }
                }
                //Обрабатываем последний подпроект
                while (!stack.empty()) {
                    val file = File("${stack.peek().second}\\build\\$fileName")
                    file.writeText(stack.peek().first.digest().toHexString())
                    stack.pop()
                }
            }
        }
    }

    private fun ByteArray.toHexString(): String {
        return this.joinToString("") {
            java.lang.String.format("%02x", it)
        }
    }
}