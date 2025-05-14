package org.dsl

import kotlinx.coroutines.*
import org.dsl.CheckCommands.Companion.runAllChecks
import org.dsl.Config.Companion.getCheckTasks
import org.dsl.Config.Companion.getGroups
import org.dsl.Config.Companion.getTasks
import org.dsl.Config.Companion.read

class Controller(val args: Array<String>) {

    fun help() {
        println("oop-checker:")
        println("        --help | -h___________________________________print this message;")
        println("        --check_______________________________________checks tasks of all students;")
        println("        --check cfg___________________________________checks by config plan;")
        println("        --check --student <student_name>______________checks all tasks of the selected student;")
        println("        --check --student <student_name> <task>_______checks tasks of the selected student;")
        println("        --check --group <group_id>____________________checks all tasks for all students of the selected group;")
    }

    fun check() {
        read()
        when {
            args.size == 1 -> checkAll()
            args.size == 2 && args[1] == "cfg" -> checkCfg()
            args.size == 3 && args[1] == "--student" -> checkCurStudent()
            args.size == 4 && args[1] == "--student" -> checkCurStudentTask()
            args.size == 3 && args[1] == "--group" -> checkGroup()
            else -> println("oop-checker: Invalid arguments. Use --help | -h for usage info.")
        }
    }

    private fun checkAll() = runBlocking {
        val tasks = getTasks()
        val groups = getGroups()
        val students = ArrayList<Student>()
        val coroutines = ArrayList<CoroutineTask>()

        for (group in groups) {
            group.students.forEach {
                it.setGroup(group.name)
                students.add(it)
            }
        }

        for (task in tasks) {
            coroutines.add(CoroutineTask(task, students))
        }

        coroutineScope {
            coroutines.map { async { it.check() } }.awaitAll()
        }

        generateHtmlReport(students, "report_all.html")
    }

    private fun checkCfg() = runBlocking {
        val groups = getGroups()
        val tasks = getTasks()
        val checkTasks = getCheckTasks()
        val students = ArrayList<Student>()
        val coroutines = ArrayList<CoroutineTask>()
        val checker = mutableMapOf<Task, ArrayList<Student>>()

        groups.groups.forEach { group ->
            group.students.forEach {
                it.setGroup(group.name)
                students.add(it)
            }
        }

        for (checkTask in checkTasks) {
            for (task in tasks) {
                if (task.id == checkTask.task) {
                    val neededStudents = ArrayList<Student>()
                    for (checkStudent in checkTask.students) {
                        for (student in students) {
                            if (checkStudent == student.github) {
                                neededStudents.add(student)
                            }
                        }
                    }
                    checker[task] = neededStudents
                    break
                }
            }
        }

        for (check in checker) {
            coroutines.add(CoroutineTask(check.key, check.value))
        }

        coroutineScope {
            coroutines.map { async { it.check() } }.awaitAll()
        }

        generateHtmlReport(students, "report_cfg.html")
    }

    private fun checkCurStudent() = runBlocking {
        val student = findStudent(getGroups())
        val tasks = getTasks()
        val coroutines = ArrayList<CoroutineTask>()

        if (student.github == "EXIT") {
            throw IllegalArgumentException("ERROR: student ${args[2]} doesn't exist")
        }

        val array = arrayListOf(student)

        for (task in tasks) {
            coroutines.add(CoroutineTask(task, array))
        }

        coroutineScope {
            coroutines.map { async { it.check() } }.awaitAll()
        }

        generateHtmlReport(array, "report_cur_student.html")
    }

    private fun checkCurStudentTask() {
        val tasks = getTasks()
        val groups = getGroups()
        val student = findStudent(groups)
        val task = findTask(tasks)

        if (student.github == "EXIT") {
            throw IllegalArgumentException("ERROR: student ${args[2]} doesn't exist")
        }

        if (task.id == "EXIT") {
            throw IllegalArgumentException("ERROR: task ${args[3]} doesn't exist")
        }

        runAllChecks(student, task)

        generateHtmlReport(listOf(student), "report_cur_student_task.html")
    }

    private fun checkGroup() = runBlocking {
        val groups = getGroups()
        val tasks = getTasks()
        val students = ArrayList<Student>()
        var selectedGroup: Group? = null
        val coroutine = ArrayList<CoroutineTask>()

        for (group in groups) {
            if (group.name == args[2]) {
                selectedGroup = group
                break
            }
        }

        if (selectedGroup == null) {
            throw IllegalArgumentException("ERROR: group ${args[2]} doesn't exist")
        }

        selectedGroup.students.forEach {
            it.setGroup(selectedGroup.name)
            students.add(it)
        }

        for (task in tasks) {
            coroutine.add(CoroutineTask(task, students))
        }

        coroutineScope {
            coroutine.map { async { it.check() } }.awaitAll()
        }

        generateHtmlReport(students, "report_group_${selectedGroup.name}.html")
    }

    private fun findTask(tasks: Tasks): Task {
        for (task in tasks) {
            if (task.id == args[3]) {
                return task
            }
        }
        return Task("EXIT", "EXIT", 1, "EXIT", "EXIT")
    }

    private fun findStudent(groups: Groups): Student {
        for (group in groups) {
            for (student in group.students) {
                if (student.github == args[2]) {
                    student.setGroup(group.name)
                    return student
                }
            }
        }
        return Student("EXIT", "EXIT", "EXIT")
    }
}
