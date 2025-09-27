package person

import data.Enrollment
import kotlinx.serialization.Serializable
import repository.Repos
import java.lang.String.format

@Serializable
class Student(
    override var id: Int,
    override var name: String,
    override var age: Int? = null,
    val listEnrollments: MutableSet<Enrollment> = mutableSetOf()
) : People(id, name, age), AverageScore {

    constructor(ma: Int, ten: String) : this(ma, ten, null)

    override fun calculateAverageScore(): Double {
        if (listEnrollments.isEmpty()) return 0.0
        var sumCredits = 0
        var sum = 0.0
        for (enroll in listEnrollments) {
            val clazz = Repos.classRepo.findById(enroll.classId) ?: continue
            val subject = Repos.subjectRepo.findById(clazz.subjectId) ?: continue
            sumCredits += subject.credits
            sum += enroll.grade * subject.credits
        }
        if (sumCredits == 0) return 0.0
        return format("%.2f", sum / sumCredits).toDouble()
    }

    override fun printAll() {
        super.printAll()
        if (listEnrollments.isEmpty()) {
            println("Sinh viên $name - $id: Chưa có môn học")
        } else {
            println("Sinh viên $name - $id: Danh sách môn học:")
            listEnrollments.forEachIndexed { i, enroll ->
                val clazz = Repos.classRepo.findById(enroll.classId) ?: return@forEachIndexed
                val subject = Repos.subjectRepo.findById(clazz.subjectId) ?: return@forEachIndexed
                val teacher = Repos.teacherRepo.findById(clazz.teacherId)
                println("Môn ${i + 1}: Tên: ${subject.name}, Mã: ${subject.id}, Mã lớp: ${clazz.id}, Điểm: ${enroll.grade}, Số tín chỉ: ${subject.credits}, Giảng viên: ${teacher?.name ?: "Chưa có"}")
            }
        }
        println("Điểm trung bình: ${calculateAverageScore()}")
        println("--------------------------------------------------")
    }

    companion object {
        private var AUTO_ID = 1000
        fun getAutoId(): Int {
            return AUTO_ID++
        }
    }
}