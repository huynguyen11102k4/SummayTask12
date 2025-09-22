package data

import person.Teacher

object TeacherRepo {
    private val teachers = mutableListOf<Teacher>()

    fun addTeacher(teacher: Teacher) {
        teachers.add(teacher)
    }

    fun printAll() {
        if (teachers.isEmpty()) {
            println("Danh sách giảng viên trống")
        } else {
            teachers.forEach { it.printAll() }
        }
    }

    fun findById(id: Int): Teacher? {
        return teachers.find { it.id == id }
    }

    fun removeById(id: Int): Boolean {
        val teacher = findById(id)
        return if (teacher != null) {
            teachers.remove(teacher)
            true
        } else {
            false
        }
    }

    fun all(): MutableList<Teacher> {
        return teachers
    }
}