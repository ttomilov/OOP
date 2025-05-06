package org.dsl

class Groups(var groups: ArrayList<Group> = ArrayList()) {

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

    operator fun iterator(): MutableIterator<Group> = groups.iterator()
}

class Group(var name: String,
            var students: ArrayList<Student>) {

    fun student(github: String = "",
                fullName: String = "",
                repo: String = "")
    {
        students.add(Student(github, fullName, repo))

    }
}