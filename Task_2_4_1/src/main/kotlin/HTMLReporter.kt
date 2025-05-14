package org.dsl

import java.io.File
import kotlinx.html.*
import kotlinx.html.stream.createHTML

fun generateHtmlReport(students: List<Student>, fileName: String) {
    val grouped = students.groupBy { it.getGroup() }

    val html = createHTML().html {
        head {
            title { +"Отчёт по проверке" }
            style {
                +"""
                @import url('https://fonts.googleapis.com/css2?family=JetBrains+Mono&display=swap');
                body {
                    font-family: 'JetBrains Mono', monospace;
                    margin: 20px;
                }
                table, th, td {
                    border: 1px solid #ccc;
                    border-collapse: collapse;
                    padding: 6px;
                    text-align: center;
                }
                th { background-color: #f2f2f2; }
                .ok { color: green; font-weight: bold; }
                .fail { color: red; font-weight: bold; }
                """.trimIndent()
            }
        }
        body {
            h2 { +"Результаты:" }

            for ((groupName, groupStudents) in grouped) {
                h3 { +"Группа: $groupName" }

                table {
                    tr {
                        th { +"Студент" }
                        th { +"Задание" }
                        th { +"Build" }
                        th { +"Checkstyle" }
                        th { +"Test" }
                    }

                    for (student in groupStudents) {
                        val results = student.getResults()
                        for ((index, res) in results.withIndex()) {
                            tr {
                                if (index == 0) {
                                    td { +student.fullName.ifBlank { student.github } }
                                } else {
                                    td {}
                                }

                                td { +res.taskName }

                                td {
                                    classes = setOf(if (res.build) "ok" else "fail")
                                    +if (res.build) "✔" else "✘"
                                }
                                td {
                                    classes = setOf(if (res.checkstyle) "ok" else "fail")
                                    +if (res.checkstyle) "✔" else "✘"
                                }
                                td {
                                    classes = setOf(if (res.test) "ok" else "fail")
                                    +if (res.test) "✔" else "✘"
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    File(fileName).writeText(html)
}
