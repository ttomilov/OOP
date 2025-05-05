package org.dsl

import java.io.File
import org.dsl.Student.Result

class CoroutineCheck(private val task: Task, private val students: ArrayList<Student>) {
    private val git = GitCommands()
    val checkCommands = CheckCommands()

    fun check() {
        for (student in students) {
            git.clone(student)
            val build = checkCommands.build(student, task)
            if (!build){
                student.addResult(Result(task.name, build = false, test = false, checkstyle = false))
                return
            }
            val test = checkCommands.tests(student, task)
            val style = checkCommands.checkstyle(student, task)
            student.addResult(Result(task.name, true, test, style))
        }
    }
}