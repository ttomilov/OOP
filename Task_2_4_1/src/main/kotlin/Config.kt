package org.dsl

import javax.script.ScriptEngineManager

class Config {
    private val tasks: Tasks = Tasks()
    private val groups: Groups = Groups()
    private val checkTasks : CheckTasks = CheckTasks()

    fun read(){
        val config = this::class.java.classLoader.getResourceAsStream("config")!!
        val reader = config.reader()
        val engine = ScriptEngineManager().getEngineByExtension("kts")!!

        engine.put("tasks", tasks)
        engine.put("groups", groups)
        engine.put("checkTasks", checkTasks)

        engine.eval(reader)
    }

    fun getTasks(): Tasks {
        return tasks
    }

    fun getGroups(): Groups {
        return groups
    }

    fun getCheckTasks(): CheckTasks {
        return checkTasks
    }
}