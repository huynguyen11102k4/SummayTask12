package service

import data.Subject
import extension.customSort
import extension.print
import extension.top10
import formatString
import person.Student
import readInt
import repository.Repos
import repository.StudentRepo
import utils.FileUtils
import utils.Outcome
import utils.Sort

class StudentService(
    val enrollmentService: EnrollmentService = EnrollmentService()
) {

    fun addStudent(): Outcome {
        println("Thêm sinh viên - Nhập thông tin sinh viên:")
        println("Bước 1: Nhập mã sinh viên (hoặc để trống để tự động tạo mã):")
        val studentId = readLine()?.toIntOrNull() ?: Student.getAutoId()
        if (Repos.studentRepo.findById(studentId) != null) {
            println("Lỗi: Mã sinh viên $studentId đã tồn tại")
            return Outcome.Failure("Vui lòng nhập lại")
        }

        println("Bước 2: Nhập tên sinh viên:")
        val name = formatString()

        println("Bước 3: Nhập tuổi sinh viên:")
        val age = readLine()?.toIntOrNull()

        println("Bước 4: Nhập số môn học:")
        val numSubjects = readLine()?.toIntOrNull() ?: 0
        val enrollments = mutableSetOf<data.Enrollment>()
        repeat(numSubjects) {
            enrollments.add(enrollmentService.inputEnrollmentInfo())
        }

        val student = Student(studentId, name, age, enrollments)
        Repos.studentRepo.add(student)
        FileUtils.saveStudents(Repos.studentRepo.all())
        return Outcome.Success("Thêm sinh viên thành công")
    }

    fun printStudentInfo(): Outcome {
        Repos.studentRepo.printAll()
        return Outcome.Success("In danh sách sinh viên thành công")
    }

    fun findStudent(): Outcome {
        val id = readInt("Nhập mã sinh viên cần tim:")
        val student = Repos.studentRepo.findById(id)
        return if (student != null) {
            student.printAll()
            Outcome.Success("Tìm thấy sinh viên có mã $id")
        } else {
            Outcome.Failure("Không tìm thấy sinh viên có mã $id")
        }
    }

    fun deleteStudent(): Outcome {
        val id = readInt("Nhập mã sinh viên cần xóa:")
        return if (Repos.studentRepo.removeById(id)) {
            FileUtils.saveStudents(Repos.studentRepo.all())
            Outcome.Success("Xóa sinh viên thành công sinh viên có mã $id")
        } else {
            Outcome.Failure("Không tìm thấy sinh viên có mã $id")
        }
    }

    fun printTop10(): Outcome {
        val students = Repos.studentRepo.all()
        students.top10().forEach { it.printAll() }
        return Outcome.Success("Trên đây là top 10 sinh viên có điểm trung bình cao nhất")
    }

    fun sortOption(choice: Int, ascending: Boolean): Outcome {
        val sorted = when (choice) {
            1 -> Sort.ById(ascending).customSort(Repos.studentRepo)
            2 -> Sort.ByName(ascending).customSort(Repos.studentRepo)
            3 -> Sort.ByScore(ascending).customSort(Repos.studentRepo)
            else -> return Outcome.Failure("Lựa chọn không hợp lệ")
        }
        sorted.forEach { it.printAll() }
        return Outcome.Success("Sắp xếp sinh viên thành công")
    }

    fun sortStudents(): Outcome {
        println("Sắp xếp sinh viên theo: 1. Mã   2. Tên     3. Điểm trung bình")
        val choice = readInt("Nhập lựa chọn của bạn (1-3), nhập khác để thoát:")
        if (choice < 1 || choice > 3) {
            return Outcome.Failure("Lựa chọn không hợp lệ")
        }
        println("a. Tăng dần   b. Giảm dần")
        val order = readLine()
        return if (order != "a" && order != "b") {
            Outcome.Failure("Lựa chọn không hợp lệ")
        } else if (order == "a") {
            sortOption(choice, true)
        } else {
            sortOption(choice, false)
        }
    }

    fun editStudent(): Outcome {
        val id = readInt("Nhập mã sinh viên cần sửa:")
        val student = Repos.studentRepo.findById(id) ?: return Outcome.Failure("Không tìm thấy sinh viên có mã $id")

        println("Chỉnh sửa sinh viên - Lựa chọn thông tin cần chỉnh sửa:")
        val menu = listOf(
            "1. Tên", "2. Tuổi", "3. Thêm môn học", "4. Xóa môn học", "5. Chỉnh sửa môn học"
        ).joinToString("\n")
        println(menu)

        while (true){
            val choice = readInt("Nhập lựa chọn của bạn (1-5), nhập khác để thoát:")
            val result: Outcome = when (choice){
                1 -> {
                    println("Nhập tên sinh viên mới:")
                    val newName = formatString()
                    student.name = newName
                    Outcome.Success("Cập nhật tên sinh viên thành công")
                }

                2 -> {
                    println("Nhập tuổi sinh viên mới:")
                    val newAge = readInt("").takeIf { it > 0 }
                    student.age = newAge
                    Outcome.Success("Cập nhật tuổi sinh viên thành công")
                }

                3 -> {
                    enrollmentService.addSubject(student)
                }

                4 -> {
                    enrollmentService.deleteSubject(student)
                }

                5 -> {
                    enrollmentService.editSubject(student)
                }

                else -> {
                    println("Thoát chỉnh sửa thông tin sinh viên")
                    Outcome.Unknown
                }
            }

            if (result == Outcome.Unknown){
                break
            }
            result.print()
        }

        FileUtils.saveStudents(Repos.studentRepo.all())
        return Outcome.Success("Cập nhật thông tin sinh viên thành công")
    }
}