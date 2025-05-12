package org.dsl

import kotlinx.coroutines.*
import org.dsl.CheckCommands.Companion.runAllChecks
import org.dsl.Config.Companion.getCheckTasks
import org.dsl.Config.Companion.getGroups
import org.dsl.Config.Companion.getTasks
import org.dsl.Config.Companion.read
import kotlin.system.exitProcess

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
            students.addAll(group.students)
        }

        for (task in tasks) {
            coroutines.add(CoroutineTask(task, students))
        }

        async { coroutines.forEach { it.check() } }.join()

        students.forEach { student -> println(student.toString()) }
    }

    private fun checkCfg() = runBlocking {
        val groups = getGroups()
        val tasks = getTasks()
        val checkTasks = getCheckTasks()
        val stdForPrint = mutableSetOf<Student>()
        val students = ArrayList<Student>()
        val coroutines = ArrayList<CoroutineTask>()
        val checker = mutableMapOf<Task, ArrayList<Student>>()
        groups.groups.forEach { group -> students.addAll(group.students) }

        for (checkTask in checkTasks) {
            for (task in tasks) {
                if (task.id == checkTask.task){
                    val neededStudent = ArrayList<Student>()
                    for (checkStudent in checkTask.students) {
                        for (student in students) {
                            if (checkStudent == student.github){
                                neededStudent.add(student)
                                stdForPrint.add(student)
                            }
                        }
                    }
                    checker[task] = neededStudent
                    break
                }
            }
        }

        for (check in checker) {
            coroutines.add(CoroutineTask(check.key, check.value))
        }

        async { coroutines.forEach { it.check() } }.join()

        students.forEach { student -> println(student.toString()) }
    }

    private fun checkCurStudent() = runBlocking {
        val student = findStudent(getGroups())
        val tasks = getTasks()
        val coroutines = ArrayList<CoroutineTask>()

        if (student.github == "EXIT") {
            System.err.println("ERROR: student ${args[2]} doesn't exist")
            exitProcess(1)
        }
        val array = ArrayList<Student>()
        array.add(student)

        for (task in tasks) {
            coroutines.add(CoroutineTask(task, array))
        }

        async { coroutines.forEach { it.check() } }.join()

        println(student.toString())
    }

    private fun checkCurStudentTask() {
        val tasks = getTasks()
        val groups = getGroups()
        val student = findStudent(groups)
        val task = findTask(tasks)

        if (student.github == "EXIT") {
            System.err.println("ERROR: student ${args[2]} doesn't exist")
            exitProcess(1)
        }

        if (task.id == "EXIT") {
            System.err.println("ERROR: task ${args[3]} doesn't exist")
            exitProcess(1)
        }

        runAllChecks(student, task)

        println(student.toString())
    }

    private fun checkGroup() = runBlocking {
        val groups = getGroups()
        val tasks = getTasks()
        val students = ArrayList<Student>()
        var selectedGroup: Group? = null
        val coroutine = ArrayList<CoroutineTask>()

        for (group in groups) {
            if (group.name == args[3]) {
                selectedGroup = group
                break
            }
        }

        if (selectedGroup == null) {
            System.err.println("ERROR: group ${args[2]} doesn't exist")
            exitProcess(1)
        }

        selectedGroup.students.forEach { student -> students.add(student) }

        for (task in tasks) {
            coroutine.add(CoroutineTask(task, students))
        }

        async { coroutine.forEach { it.check() } }.join()

        students.forEach { student -> println(student.toString()) }
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
                    val std = student
                    std.setGroup(group.name)
                    return std
                }
            }
        }

        return Student("EXIT", "EXIT", "EXIT")
    }
}
