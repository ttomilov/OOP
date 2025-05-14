package org.dsl

import java.util.Vector

class Student(val github: String, val fullName: String, val repo: String) {

    data class Result(val taskName: String,
                      val build: Boolean,
                      val test: Boolean,
                      val checkstyle: Boolean
    )

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
}
