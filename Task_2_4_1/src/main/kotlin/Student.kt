package org.dsl

import java.util.Vector

class Student(val github: String, val fullName: String, val repo: String) {

    data class Result(val taskName: String,
                      val build: Boolean,
                      val test: Boolean,
                      val checkstyle: Boolean
    ) {
        override fun toString(): String {
            val str = StringBuilder()
            str.append(" ".repeat(taskName.length + 1) + "build " + "test " + "checkstyle\n")
            str.append(taskName + " ".repeat(3))
            if (build){
                str.append("+")
            } else {
                str.append("-")
            }
            str.append(" ".repeat(4))
            if (test) {
                str.append("+")
            } else {
                str.append("-")
            }
            str.append(" ".repeat(7))
            if (checkstyle) {
                str.append("+")
            } else {
                str.append("-")
            }
            str.append("\n")
            return str.toString()
        }
    }

    private var group: String = ""
    private var results: Vector<Result> = Vector()

    fun setGroup(group: String) {
        this.group = group
    }

    fun getGroup(): String = this.group

    fun addResult(result: Result) {
        results.add(result)
    }

    fun getResults(): Vector<Result> = this.results

    override fun toString() : String {
        val str = StringBuilder()
        str.append(group + "\n" + fullName + "\n")
        for (result in this.results) {
            str.append(result.toString())
        }
        return str.toString()
    }
}
