package data

import person.Student


object StudentRepo {
    private val students = mutableListOf<Student>()

    fun addStudent(sv: Student) {
        students.add(sv)
    }

    fun printAll() {
        for (sv in students) {
            sv.printAll()
        }
    }

    fun findById(ma: Int): Student? {
        return students.find { it.id == ma }
    }

    fun removeById(ma: Int): Boolean {
        val sv = findById(ma)
        return if (sv != null) {
            students.remove(sv)
            true
        } else {
            false
        }
    }

    fun all(): MutableList<Student> {
        return students
    }
}