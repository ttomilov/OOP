package org.dsl

import org.dsl.GitCommands.Companion.clone
import java.io.File
import org.dsl.Student.Result

class CheckCommands {

    companion object {
        fun runAllChecks(student: Student, task: Task){
            clone(student)
            val buildSuccess = build(student, task)
            if (!buildSuccess) {
                student.addResult(Result(task.name, build = false, test = false, checkstyle = false))
                return
            }
            val testSuccess = tests(student, task)
            val styleSuccess = checkstyle(student, task)
            student.addResult(Result(task.id, build = true, test = testSuccess, checkstyle = styleSuccess))
        }

        private fun build(student: Student, task: Task): Boolean {
            val runtime = Runtime.getRuntime()
            val dir = File("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id + File.separator)
            val gradle = arrayOf("./gradlew", "build")

            return runtime.exec(gradle, null, dir).waitFor() == 0
        }

        private fun checkstyle(student: Student, task: Task) : Boolean {
            return true
        }

        private fun tests(student: Student, task: Task): Boolean {
            val runtime = Runtime.getRuntime()
            val dir = File("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id + File.separator)
            val gradle = arrayOf("./gradlew", "test")

            return runtime.exec(gradle, null, dir).waitFor() == 0
        }
    }
}