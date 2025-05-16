package org.dsl

class CheckTasks(var tasks: ArrayList<CheckTask> = ArrayList()) {

    fun task(task: String, init: CheckTask.() -> Unit) {
        val checkTask = CheckTask(task, ArrayList())
        tasks.add(checkTask.apply(init))
    }

    operator fun invoke(init: CheckTasks.() -> Unit) {
        apply(init)
    }

    operator fun iterator(): Iterator<CheckTask> = tasks.iterator()
}

class CheckTask(var task: String, var students: ArrayList<String>) {
    fun check(name : String) {
        students.add(name)
    }
}