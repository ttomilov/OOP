package org.dsl

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.dsl.GitCommands.Companion.clone
import java.io.File
import org.dsl.Student.Result
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.Path

class CheckCommands {

    companion object {
        private val logger: Logger = LogManager.getLogger(CheckCommands::class.java)
        private val lock = Any()

        fun runAllChecks(student: Student, task: Task) {
            log("Run check for ${student.github} in ${task.id}")
            clone(student)
            val path = Path("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id)
            if (!Files.exists(path)) {
                synchronized(lock) {
                    student.addResult(Result(task.name + "(" + task.id + ")", build = false, test = false, checkstyle = false))
                }
                return
            }
            synchronized(lock) {
                student.addResult(Result(task.name + "(" + task.id + ")", build(student, task), tests(student, task), checkstyle(student, task)))
            }
        }

        private fun runGradleCommand(command: String, dir: File): Boolean {
            val isWindows = System.getProperty("os.name").lowercase().contains("win")
            val gradleCmd = if (isWindows) listOf("cmd", "/c", "gradlew.bat", command) else listOf("./gradlew", command)

            val process = ProcessBuilder(gradleCmd)
                .directory(dir)
                .start()

            return process.waitFor() == 0
        }

        private fun build(student: Student, task: Task): Boolean {
            val dir = File("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id)
            return runGradleCommand("assemble", dir)
        }

        private fun checkstyle(student: Student, task: Task): Boolean {
            val taskDir = File("repositories" + File.separator + student.github + File.separator + File.separator + task.id + File.separator + "src")
            val jar = javaClass.getResource("/jar/checkstyle-10.23.1-all.jar")
            val cfg = javaClass.getResource("/xml/google_checks.xml")

            if (cfg == null) {
                log("WARNING: No style config found!")
                return false
            }

            if (jar == null) {
                log("WARNING: No checkstyle jar found!")
                return false
            }

            if (!taskDir.exists()) {
                log("WARNING: No ${task.id} in ${student.github} found!")
                return false
            }

            val jarPath = Paths.get(jar.toURI()).toString()
            val cfgPath = Paths.get(cfg.toURI()).toString()

            val commands = listOf("java", "-jar", jarPath, "-c", cfgPath, taskDir.absolutePath)

            val process = ProcessBuilder(commands)
                .directory(taskDir)
                .redirectErrorStream(true)
                .start()

            val output = process.inputStream.bufferedReader().readText()
            val exitCode = process.waitFor()

            if (exitCode > 1) {
                log("ERROR in producing checkstyle jar, exitCode=${exitCode}")
                return false
            }

            val errorLines = output.lines().filter { it.contains("[ERROR]") }

            return errorLines.size <= 10
        }

        private fun tests(student: Student, task: Task): Boolean {
            val dir = File("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id)
            return runGradleCommand("test", dir)
        }
        
        private fun log(msg: String){
            synchronized(lock){
                logger.info(msg)
                println(msg)
            }
        }
    }
}