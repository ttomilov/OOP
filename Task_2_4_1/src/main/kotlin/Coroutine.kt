package org.dsl

import org.dsl.CheckCommands.Companion.runAllChecks

class CoroutineTask(private val task: Task, private val students: ArrayList<Student>) {

    fun check() {
        for (student in students) {
            runAllChecks(student, task)
        }
    }
}

class CoroutineCheckAll {

}