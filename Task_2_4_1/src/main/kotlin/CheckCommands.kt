package org.dsl

import org.dsl.GitCommands.Companion.clone
import java.io.File
import org.dsl.Student.Result
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.Path

class CheckCommands {

    companion object {
        fun runAllChecks(student: Student, task: Task) {
            clone(student)
            val path =
                Path("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id)
            if (!Files.exists(path)) {
                student.addResult(Result(task.name + "(" + task.id + ")", build = false, test = false, checkstyle = false))
                return
            }

            student.addResult(Result(task.name + "(" + task.id + ")", build(student, task), tests(student, task), checkstyle(student, task)))
        }

        private fun runGradleCommand(command: String, dir: File): Boolean {
            val isWindows = System.getProperty("os.name").lowercase().contains("win")
            val gradleCmd = if (isWindows) listOf("cmd", "/c", "gradlew.bat", command) else listOf("./gradlew", command)

            val process = ProcessBuilder(gradleCmd)
                .directory(dir)
                .inheritIO()
                .start()

            return process.waitFor() == 0
        }

        private fun build(student: Student, task: Task): Boolean {
            val dir = File("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id)
            return runGradleCommand("assemble", dir)
        }

        private fun checkstyle(student: Student, task: Task): Boolean {
            val baseDir = Paths.get("repositories", student.github, "OOP", task.id).toFile()
            val srcDir = File(baseDir, "src")

            val jarFile = File("resources/checkstyle-10.12.1-all.jar")
            val configFile = File("resources/google_checks.xml")

            if (!srcDir.exists() || !jarFile.exists() || !configFile.exists()) {
                println("Checkstyle skipped: missing src, jar or config for ${student.github} task ${task.id}")
                return false
            }

            val command = listOf(
                "java", "-jar", jarFile.absolutePath,
                "-c", configFile.absolutePath,
                srcDir.absolutePath
            )

            return try {
                val process = ProcessBuilder(command)
                    .directory(baseDir)
                    .redirectErrorStream(true)
                    .start()

                val output = process.inputStream.bufferedReader().readText()
                println(output)

                val exitCode = process.waitFor()
                exitCode == 0
            } catch (e: Exception) {
                println("Checkstyle error for ${student.github} task ${task.id}: ${e.message}")
                false
            }
        }

        private fun tests(student: Student, task: Task): Boolean {
            val dir = File("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id)
            return runGradleCommand("test", dir)
        }
    }
}