import javax.script.ScriptEngineManager

class Config {
    private val tasks: Tasks = Tasks()
    private val groups: Groups = Groups()

    fun read(){
        val config = this::class.java.classLoader.getResourceAsStream("config.chk")!!
        val reader = config.reader()
        val engine = ScriptEngineManager().getEngineByExtension("kts")!!

        engine.put("tasks", tasks)
        engine.put("groups", groups)

        engine.eval(reader)
    }

    fun getTasks(): Tasks {
        return tasks
    }

    fun getGroups(): Groups {
        return groups
    }
}