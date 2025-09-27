package repository

import person.Teacher

class TeacherRepo : Repository<Teacher, Int> {
    private val teachers = mutableListOf<Teacher>()

    override fun add(item: Teacher) {
        teachers.add(item)
    }

    override fun findById(id: Int): Teacher? {
        return teachers.find { it.id == id }
    }

    override fun removeById(id: Int): Boolean {
        return teachers.removeIf { it.id == id }
    }

    override fun all(): List<Teacher> {
        return teachers.toList()
    }

    override fun printAll() {
        if (teachers.isEmpty()) {
            println("No teachers available.")
        } else {
            teachers.forEach { it.printAll()}
        }
    }
}