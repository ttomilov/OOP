class Groups(var groups: ArrayList<Group> = ArrayList()) {
    class Group(var name: String,
                var students: ArrayList<Student>) {

        data class Student(val github: String = "",
                           val fullName: String = "",
                           val repo: String = "")

        fun student(github: String = "",
                    fullName: String = "",
                    repo: String = "")
        {
            students.add(Student(github, fullName, repo))

        }
    }

    fun group(name: String, init: Group.() -> Unit) : Group {
        val group = Group(name, ArrayList())
        group.name = name
        groups.add(group)
        init(group)
        return group
    }


    operator fun invoke(init: Groups.() -> Unit) {
        apply(init)
    }
}