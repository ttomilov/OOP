package org.dsl

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.dsl.Student.Result
import kotlin.system.exitProcess

class Controller(val args: Array<String>) {
    private val config: Config = Config()
    private val git = GitCommands()
    private val checkCommands = CheckCommands()

    fun help() {
        println("oop-checker:")
        println("        --help | -h_________________________print this message")
        println("        --check <student_name>______________checks all tasks of the selected student")
        println("        --check <student_name> <task>_______checks tasks of the selected student")
        println("        --check_____________________________checks tasks of all students")
        println("        --check cfg_________________________checks by config plan")
    }

    fun check() {
        config.read()
        when {
            args.size == 1 -> checkAll()
            args.size == 2 && args[1] == "cfg" -> checkCfg()
            args.size == 2 -> checkCurStudent()
            args.size >= 3 -> checkCurStudentTask()
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
        val groups = config.getGroups()
        val student = findStudent(groups)
        val tasks = config.getTasks()
        val coroutine = ArrayList<CoroutineCheck>()

        if (student.github == "EXIT") {
            System.err.println("ERROR: student ${args[1]} doesn't exist")
            exitProcess(1)
        }
        val array = ArrayList<Student>()
        array.add(student)

        for (task in tasks) {
            coroutine.add(CoroutineCheck(task, array))
        }

        launch { coroutine.forEach { it.check() } }
    }

    private fun checkCurStudentTask() {
        val tasks = config.getTasks()
        val groups = config.getGroups()
        val student = findStudent(groups)
        val task = findTask(tasks)

        if (student.github == "EXIT") {
            System.err.println("ERROR: student ${args[1]} doesn't exist")
            exitProcess(1)
        }

        if (task.id == "EXIT") {
            System.err.println("ERROR: task ${args[2]} doesn't exist")
            exitProcess(1)
        }

        git.clone(student)
        val build = checkCommands.build(student, task)
        if (!build) {
            student.addResult(Result(task.name, build = false, test = false, checkstyle = false))
            return
        }
        val test = checkCommands.tests(student, task)
        val style = checkCommands.checkstyle(student, task)

        student.addResult(Result(task.name, true, test, style))
    }

    private fun findTask(tasks: Tasks): Task {
        for (task in tasks) {
            if (task.id == args[2]) {
                return task
            }
        }

        return Task("EXIT", "EXIT", 1, "EXIT", "EXIT")
    }

    private fun findStudent(groups: Groups): Student {
        for (group in groups) {
            for (student in group.students) {
                if (student.github == args[1]) {
                    return student
                }
            }
        }

        return Student("EXIT", "EXIT", "EXIT")
    }
}
