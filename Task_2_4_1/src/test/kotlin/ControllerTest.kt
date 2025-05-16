package org.dsl

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import java.io.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ControllerTest {

    @BeforeEach
    fun reloadConfig() {
        Config.read()
    }

    @AfterEach
    fun cleanupReports() {
        val reportFiles = listOf(
            "report_all.html",
            "report_cfg.html",
            "report_cur_student.html",
            "report_cur_student_task.html",
            "report_group_23216.html"
        )
        reportFiles.forEach {
            val f = File(it)
            if (f.exists()) f.delete()
        }
    }

    @Test
    fun `should print help message`() {
        val out = captureStdOut {
            Controller(arrayOf("--help")).help()
        }
        Assertions.assertTrue(out.contains("oop-checker"))
        Assertions.assertTrue(out.contains("--check"))
    }

    @Test
    fun `should check all tasks for all students`() = runBlocking {
        val controller = Controller(arrayOf("--check"))
        controller.check()

        val reportFile = File("report_all.html")
        Assertions.assertTrue(reportFile.exists())
    }

    @Test
    fun `should check only config-planned tasks`() = runBlocking {
        val controller = Controller(arrayOf("--check", "cfg"))
        controller.check()

        val reportFile = File("report_cfg.html")
        Assertions.assertTrue(reportFile.exists())
    }

    @Test
    fun `should check all tasks for specific student`() = runBlocking {
        val controller = Controller(arrayOf("--check", "--student", "nkrainov"))
        controller.check()

        val reportFile = File("report_cur_student.html")
        Assertions.assertTrue(reportFile.exists())
    }

    @Test
    fun `should check specific task for specific student`() {
        val controller = Controller(arrayOf("--check", "--student", "nkrainov", "Task_2_2_1"))
        controller.check()

        val reportFile = File("report_cur_student_task.html")
        Assertions.assertTrue(reportFile.exists())
    }

    @Test
    fun `should check all tasks for group`() = runBlocking {
        val controller = Controller(arrayOf("--check", "--group", "23216"))
        controller.check()

        val reportFile = File("report_group_23216.html")
        Assertions.assertTrue(reportFile.exists())
    }

    @Test
    fun `should fail on non-existent student`() {
        val ex = assertThrows<RuntimeException> {
            Controller(arrayOf("--check", "--student", "unknown")).check()
        }
        Assertions.assertTrue(ex.message!!.contains("doesn't exist"))
    }

    @Test
    fun `should fail on non-existent task`() {
        val ex = assertThrows<RuntimeException> {
            Controller(arrayOf("--check", "--student", "nkrainov", "BadTask")).check()
        }
        Assertions.assertTrue(ex.message!!.contains("doesn't exist"))
    }

    @Test
    fun `should fail on non-existent group`() {
        val ex = assertThrows<RuntimeException> {
            Controller(arrayOf("--check", "--group", "99999")).check()
        }
        Assertions.assertTrue(ex.message!!.contains("doesn't exist"))
    }

    @Test
    fun `should print error on invalid args`() {
        val out = captureStdOut {
            Controller(arrayOf("--invalid", "--args")).check()
        }
        Assertions.assertTrue(out.contains("Invalid arguments"))
    }

    private fun captureStdOut(block: () -> Unit): String {
        val originalOut = System.out
        val baos = ByteArrayOutputStream()
        System.setOut(PrintStream(baos))
        try {
            block()
        } finally {
            System.setOut(originalOut)
        }
        return baos.toString()
    }
}
