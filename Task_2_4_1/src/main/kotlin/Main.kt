package org.dsl

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("oop-checker: not enough arguments. Type --help | -h")
        return
    }

    val controller = Controller(args)
    when (args[0]) {
        "--help" -> controller.help()
        "-h" -> controller.help()
        "--check" -> controller.check()
        else -> println("oop-checker: unknown command: ${args[0]}. Type --help | -h")
    }
}