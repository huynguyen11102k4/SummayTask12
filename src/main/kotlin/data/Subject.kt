package data

import person.Teacher

data class Subject(
    var id: String? = null,
    var name: String = "",
    var grade: Double = 0.0,
    var credits: Int = 0,
    var teacher: Teacher? = null
)