package org.dsl

import org.dsl.GitCommands.Companion.clone
import java.io.File
import org.dsl.Student.Result
import java.nio.file.Files
import kotlin.io.path.Path

class CheckCommands {

    companion object {
        fun runAllChecks(student: Student, task: Task){
            clone(student)
            val path = Path("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id)
            if (!Files.exists(path)){
                student.addResult(Result(task.name, build = false, test = false, checkstyle = false))
                return
            }

            student.addResult(Result(task.name, build(student, task), tests(student, task), checkstyle(student, task)))
        }

        private fun build(student: Student, task: Task): Boolean {
            val runtime = Runtime.getRuntime()
            val dir = File("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id)
            val gradle = arrayOf("./gradlew", "assemble")
            return runtime.exec(gradle, null, dir).waitFor() == 0
        }

        private fun checkstyle(student: Student, task: Task) : Boolean {
            return true
        }

        private fun tests(student: Student, task: Task): Boolean {
            val runtime = Runtime.getRuntime()
            val dir = File("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id)
            val gradle = arrayOf("./gradlew", "test")

            return runtime.exec(gradle, null, dir).waitFor() == 0
        }
    }
}