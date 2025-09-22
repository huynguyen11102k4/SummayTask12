package person

import data.Subject

class Teacher(
    override var id: Int,
    override var name: String,
    override var age: Int? = null,
    val listSubjects: MutableSet<Subject> = mutableSetOf()
) : People(id, name, age) {

    constructor(id: Int, name: String) : this(id, name, null)

    override fun printAll() {
        println("Mã giảng viên: $id")
        println("Họ tên: $name")
        println("Tuổi: ${age ?: "Chưa cung cấp"}")
        if (listSubjects.isEmpty()) {
            println("Giảng viên $name - $id: Chưa phụ trách môn học nào")
        } else {
            println("Giảng viên $name - $id: Danh sách môn học phụ trách:")
            listSubjects.forEachIndexed { i, mh ->
                println("Môn ${i + 1}: Tên: ${mh.name}, Mã: ${mh.id}, Số tín chỉ: ${mh.credits}")
            }
        }
        println("--------------------------------------------------")
    }

    companion object {
        private var AUTO_ID = 2000
        fun getAutoId(): Int {
            return AUTO_ID++
        }
    }
}