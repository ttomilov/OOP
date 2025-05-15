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
            val path = Path("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id)
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
            val taskDir = File("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id + "src")
            val jar = javaClass.getResource("/checkstyle-10.23.1-all.jar")
            val cfg = javaClass.getResource("/google_checks.jar")

            if (cfg == null) {
                println("WARNING: No style config found!")
                return false
            }

            if (jar == null) {
                println("WARNING: No checkstyle jar found!")
                return false
            }

            if (!taskDir.exists()) {
                println("WARNING: No ${task.id} in ${student.github} found!")
                return false
            }

            val commands = listOf("java", "-jar", jar.toString(), "-c", cfg.toString(), taskDir.absolutePath.toString())

            val process = ProcessBuilder(commands)
                .directory(taskDir)
                .inheritIO()
                .start()

            return process.waitFor() == 0
        }

        private fun tests(student: Student, task: Task): Boolean {
            val dir = File("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id)
            return runGradleCommand("test", dir)
        }
    }
}