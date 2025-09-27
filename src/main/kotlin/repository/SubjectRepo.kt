package repository

import data.Subject

class SubjectRepo : Repository<Subject, String> {
    private val subjects = mutableSetOf<Subject>()

    override fun add(item: Subject) {
        subjects.add(item)
    }

    override fun findById(id: String): Subject? {
        return subjects.find { it.id == id }
    }

    override fun removeById(id: String): Boolean {
        return subjects.removeIf { it.toString() == id }
    }

    override fun all(): List<Subject> {
        return subjects.toList()
    }

    override fun printAll() {
        if (subjects.isEmpty()) {
            println("No subjects available.")
        } else {
            subjects.forEach { println("ID: ${it.id}, Name: ${it.name}, Credits: ${it.credits}") }
        }
    }
}