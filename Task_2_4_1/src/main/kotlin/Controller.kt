class Controller(val args: Array<String>) {
    private val config: Config = Config()

    fun help() {
        println("oop-checker:")
        println("        --help | -h_________________________print this message");
        println("        --check <student_name>______________checks all tasks of the selected student")
        println("        --check <student_name> <task>_______checks tasks of the selected student")
        println("        --check_____________________________checks tasks of all students")
    }

    fun check() {
        config.read()
        when(args.size){
            1 -> checkAll()
            else -> checkCurStudent()
        }
    }

    private fun checkAll(){
        val tasks = config.getTasks()
        val groups = config.getGroups()

//        for (group in groups){
//
//        }
    }

    private fun checkCurStudent() {


    }

    private fun checkTask(task: String) {

    }

    private fun checkstyle(){

    }

    private fun build(){

    }
}
