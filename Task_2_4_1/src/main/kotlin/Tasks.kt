class Tasks {
    val all = mutableListOf<Task>()

    fun task(id: String, name: String, maxPoints: Int, softDeadline: String, hardDeadline: String) {
        all += Task(id, name, maxPoints, softDeadline, hardDeadline)
    }
}

data class Task(
    val id: String,
    val name: String,
    val maxPoints: Int,
    val softDeadline: String,
    val hardDeadline: String
)
