package org.jetbrains.internship

import org.gradle.api.Incubating
import org.gradle.workers.WorkAction
import java.io.File
import java.security.MessageDigest
import javax.inject.Inject

@Incubating
abstract class CalculateHash @Inject constructor() : WorkAction<CalculateHashInterface> {
    override fun execute() {
        //Для каждого из подпроектов, которые мы считаем, обновляем хеш и все вспомоготальные
        with(parameters) {
            //Удаление нужно чтоыб избежать случая, когда файл был удален,потом обновили хеши,
            // а потом файл возвращен в неизменном виде
            deleteOldFiles(dir.get(), hash.get())
            val projectDigest = MessageDigest.getInstance(hash.get())
            val extensionsArray = arrayListOf("kt", "java")
            //обновляем хеши для всех файлов, для инкрементальности
            File(dir.get()).walkTopDown().forEach { currentPath ->
                if (extensionsArray.contains(currentPath.extension)) {
                    val path = currentPath.absolutePath
                    val fileHashPath = path.removePrefix("${dir.get()}\\").removeSuffix(currentPath.name)
                    val hashFileDir = File("${dir.get()}\\build\\fileHashes\\$fileHashPath")
                    hashFileDir.mkdirs()
                    val hashFile =
                        File(
                            "${hashFileDir.absolutePath}\\${currentPath.nameWithoutExtension}" +
                                    getHashFileExtension(hash.get())
                        )
                    val fileDigest = MessageDigest.getInstance(hash.get())
                    val textBytes = currentPath.readBytes()
                    fileDigest.update(textBytes, 0, textBytes.size)
                    projectDigest.update(textBytes, 0, textBytes.size)
                    hashFile.writeText(fileDigest.digest().toHexString())
                }
            }
            File("${dir.get()}\\build\\hash${getHashFileExtension(hash.get())}").writeText(
                projectDigest.digest().toHexString()
            )
        }
    }
}