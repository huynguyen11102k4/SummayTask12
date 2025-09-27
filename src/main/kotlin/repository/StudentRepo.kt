package repository

import person.Student

class StudentRepo : Repository<Student, Int> {
    private val students = mutableListOf<Student>()

    override fun add(item: Student){
        students.add(item)
    }

    override fun findById(id: Int): Student? {
        return students.find { it.id == id }
    }

    override fun removeById(id: Int): Boolean {
        return students.removeIf { it.id == id }
    }

    override fun all(): MutableList<Student> {
        return students
    }

    override fun printAll() {
        if (students.isEmpty()) {
            println("No students available.")
        } else {
            students.forEach { it.printAll() }
        }
    }
}