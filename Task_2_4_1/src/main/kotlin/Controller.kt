package org.dsl

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.dsl.CheckCommands.Companion.runAllChecks
import kotlin.system.exitProcess

class Controller(val args: Array<String>) {
    private val config: Config = Config()

    fun help() {
        println("oop-checker:")
        println("        --help | -h_________________________print this message;")
        println("        --check --student <student_name>______________checks all tasks of the selected student;")
        println("        --check --student <student_name> <task>_______checks tasks of the selected student;")
        println("        --check_____________________________checks tasks of all students;")
        println("        --check cfg_________________________checks by config plan;")
        println("        --check --group <group_id>__________________checks all tasks for all students of the selected group;")
    }

    fun check() {
        config.read()
        when {
            args.size == 1 -> checkAll()
            args.size == 2 && args[1] == "cfg" -> checkCfg()
            args.size == 3 && args[1] == "--student" -> checkCurStudent()
            args.size == 4 && args[1] == "--student" -> checkCurStudentTask()
            args.size == 3 && args[1] == "--group" -> checkGroup()
            else -> println("oop-checker: Invalid arguments. Use --help for usage info.")
        }
    }


    private fun checkAll() {
        val tasks = config.getTasks()
        val groups = config.getGroups()

    }

    private fun checkCfg() = runBlocking {

    }

    private fun checkCurStudent() = runBlocking {
        val student = findStudent(config.getGroups())
        val tasks = config.getTasks()
        val coroutine = ArrayList<CoroutineTask>()

        if (student.github == "EXIT") {
            System.err.println("ERROR: student ${args[2]} doesn't exist")
            exitProcess(1)
        }
        val array = ArrayList<Student>()
        array.add(student)

        for (task in tasks) {
            coroutine.add(CoroutineTask(task, array))
        }

        launch { coroutine.forEach { it.check() } }

        println(student.toString())
    }

    private fun checkCurStudentTask() {
        val tasks = config.getTasks()
        val groups = config.getGroups()
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
        val groups = config.getGroups()
        val tasks = config.getTasks()
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

        for (task in tasks){
            coroutine.add(CoroutineTask(task, students))
        }

        launch { coroutine.forEach { it.check() } }

        for (student in students){
            println(student.toString())
        }
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
                    return student
                }
            }
        }

        return Student("EXIT", "EXIT", "EXIT")
    }
}
