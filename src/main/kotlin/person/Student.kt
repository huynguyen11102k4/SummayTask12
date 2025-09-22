package person

import data.Subject

class Student(
    override var id: Int,
    override var name: String,
    override var age: Int? = null,
    val listSubjects: MutableSet<Subject> = mutableSetOf()
) : People(id, name, age), AverageScore {

    constructor(ma: Int, ten: String) : this(ma, ten, null)

    override fun calculateAverageScore(): Double {
        if (listSubjects.isEmpty()) {
            return 0.0
        }
        val sumCredits = listSubjects.sumOf { it.credits }
        val sum = listSubjects.sumOf { it.grade * it.credits }
        return (sum / sumCredits).let { String.format("%.2f", it).toDouble() }
    }

    override fun printAll() {
        super.printAll()
        if (listSubjects.isEmpty()) {
            println("Sinh viên $name - $id: Chưa có môn học")
        } else {
            println("Sinh viên $name - $id: Danh sách môn học:")
            listSubjects.forEachIndexed { i, mh ->
                println("Môn ${i + 1}: Tên: ${mh.name}, Điểm: ${mh.grade}, Số tín chỉ: ${mh.credits}")
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