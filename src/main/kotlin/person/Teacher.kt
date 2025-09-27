package person

import data.Subject
import kotlinx.serialization.Serializable
import repository.Repos

@Serializable
class Teacher(
    override var id: Int,
    override var name: String,
    override var age: Int? = null,
    val listClassIds: MutableSet<String> = mutableSetOf()
) : People(id, name, age) {

    constructor(id: Int, name: String) : this(id, name, null)

    override fun printAll() {
        println("Mã giảng viên: $id")
        println("Họ tên: $name")
        println("Tuổi: ${age ?: "Chưa cung cấp"}")
        if (listClassIds.isEmpty()) {
            println("Giảng viên $name - $id: Chưa phụ trách môn học nào")
        } else {
            println("Giảng viên $name - $id: Danh sách môn học phụ trách:")
            listClassIds.forEachIndexed { i, classId ->
                val clazz = Repos.classRepo.findById(classId) ?: return@forEachIndexed
                val subject = Repos.subjectRepo.findById(clazz.subjectId) ?: return@forEachIndexed
                println("Môn ${i + 1}: Tên: ${subject.name}, Mã: ${subject.id}, Số tín chỉ: ${subject.credits}, Mã lớp: $classId")
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