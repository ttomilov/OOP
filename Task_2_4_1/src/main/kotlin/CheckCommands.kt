package org.dsl

import java.io.File

class CheckCommands {

    fun build(student: Student, task: Task): Boolean {
        val runtime = Runtime.getRuntime()
        val dir = File("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id + File.separator)
        val gradle = arrayOf("./gradlew", "build")

        return runtime.exec(gradle, null, dir).waitFor() == 0
    }

    fun checkstyle(student: Student, task: Task) : Boolean {
        return true
    }

    fun tests(student: Student, task: Task): Boolean {
        val runtime = Runtime.getRuntime()
        val dir = File("repositories" + File.separator + student.github + File.separator + "OOP" + File.separator + task.id + File.separator)
        val gradle = arrayOf("./gradlew", "test")

        return runtime.exec(gradle, null, dir).waitFor() == 0
    }
}