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

class Student(val github: String, val fullName: String, val repo: String) {

    data class Result(val taskName: String,
                      val build: Boolean,
                      val test: Boolean,
                      val checkstyle: Boolean
    )

    private var group: String = ""
    private var results: ArrayList<Result> = ArrayList()

    fun setGroup(group: String) {
        this.group = group
    }

    fun getGroup(): String = this.group

    fun addResult(result: Result) {
        results.add(result)
    }

    fun getResults(): ArrayList<Result> = this.results
}