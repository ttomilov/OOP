package org.dsl

import java.io.File
import java.nio.file.Files.createDirectories
import java.nio.file.Files.exists
import kotlin.arrayOf
import kotlin.io.path.Path

class GitCommands {

    fun clone(student: Student){
        val runtime = Runtime.getRuntime()
        val path = Path("repositories" + File.separator + student.github)
        val dir = File("repositories" + File.separator + student.github)
        val git = arrayOf("git", "clone", student.repo)

        if (exists(path)){
            pull(student)
            return
        }

        createDirectories(path)
        runtime.exec(git, null, dir).waitFor()

        return
    }

    private fun pull(student: Student){
        val dir = File( "repositories" + File.separator + student.github + File.separator + "OOP")
        val git = arrayOf("git", "pull")
        val runtime = Runtime.getRuntime()

        runtime.exec(git).waitFor()
    }
}