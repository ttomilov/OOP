package org.dsl

class Tasks (val tasks: ArrayList<Task> = ArrayList()) {
    val all = mutableListOf<Task>()

    fun task(id: String, name: String, maxPoints: Int, softDeadline: String, hardDeadline: String) {
        all += Task(id, name, maxPoints, softDeadline, hardDeadline)
    }

    operator fun invoke(init: Tasks.() -> Unit) {
        apply(init)
    }

    operator fun iterator(): MutableIterator<Task> = all.iterator()
}

data class Task(
    val id: String,
    val name: String,
    val maxPoints: Int,
    val softDeadline: String,
    val hardDeadline: String
)
