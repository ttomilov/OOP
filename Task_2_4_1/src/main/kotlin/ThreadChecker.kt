package org.dsl

import org.dsl.CheckCommands.Companion.runAllChecks

class ThreadChecker(): Thread() {
    private var task: Task? = null
    private var students: ArrayList<Student>? = null

    fun init(task: Task, students: ArrayList<Student>) {
        this.task = task
        this.students = students
    }

    class ThreadTskStd(): Thread() {
        private var task: Task? = null
        private var student: Student? = null

        fun init(task: Task, student: Student) {
            this.task = task
            this.student = student
        }

        override fun run() {
            runAllChecks(student!!, task!!)
        }
    }

    override fun run() {
        for (student in students!!){
            val thread = ThreadTskStd()
            thread.init(task!!, student)
            thread.start()
        }
    }
}