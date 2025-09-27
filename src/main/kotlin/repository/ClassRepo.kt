package repository

import data.Class

class ClassRepo : Repository<Class, String> {
    private val classes = mutableListOf<Class>()

    override fun add(item: Class) {
        classes.add(item)
    }

    override fun findById(id: String): Class? {
        return classes.find { it.id == id }
    }

    override fun removeById(id: String): Boolean {
        return classes.removeIf { it.id == id }
    }

    override fun all(): List<Class> {
        return classes.toList()
    }

    override fun printAll() {
        if (classes.isEmpty()) {
            println("No classes available.")
        } else {
            classes.forEach { println("ID: ${it.id}, Subject ID: ${it.subjectId}, Teacher ID: ${it.teacherId}") }
        }
    }
}