package service

import data.Class
import data.Enrollment
import data.Subject
import formatString
import person.Student
import person.Teacher
import repository.Repos
import utils.FileUtils
import utils.Outcome
import kotlin.takeIf

class EnrollmentService {
    fun inputEnrollmentInfo() : Enrollment {
        println("Nhập thông tin môn học")
        println("Mã môn học:")
        val subjectId = readLine()?.takeIf { it.isNotBlank() } ?: run {
            println("Lỗi: Mã môn học không được để trống")
            return inputEnrollmentInfo()
        }

        var subject = Repos.subjectRepo.findById(subjectId)
        if(subject == null){
            println("Môn học chưa tồn tại, thêm mới môn học")
            println("Tên môn học:")
            val subjectName = formatString()
            if (subjectName.isBlank()) {
                println("Lỗi: Tên môn học không được để trống")
                return inputEnrollmentInfo()
            }
            println("Số tín chỉ:")
            val credits = readLine()?.toIntOrNull()?.takeIf { it >= 0 } ?: run {
                println("Lỗi: Số tín chỉ không được âm")
                return inputEnrollmentInfo()
            }
            subject = Subject(subjectId, subjectName, credits)
            Repos.subjectRepo.add(subject)
            FileUtils.saveSubjects(Repos.subjectRepo.all())
            println("Thêm môn học thành công")
        }else{
            println("Môn học đã tồn tại: ${subject.name}, Số tín chỉ: ${subject.credits}")
        }

        println("Mã lớp: ")
        val classId = readLine()?.takeIf { it.isNotBlank() } ?: run {
            println("Lỗi: Mã lớp không được để trống")
            return inputEnrollmentInfo()
        }
        var clazz = Repos.classRepo.findById(classId)
        if(clazz == null){
            println("Lớp chưa tồn tại, thêm mới")
            println("Nhập mã giảng viên phụ trách môn học (hoặc để trống để thêm mới):")
            val teacherId = readLine()?.toIntOrNull()
            val teacher = teacherId?.let {
                Repos.teacherRepo.findById(it) ?: run {
                    println("Lỗi: Không tìm thấy giảng viên có mã $teacherId")
                    return inputEnrollmentInfo()
                }
            } ?: run {
                println("Thêm giảng viên mới - Nhập tên giảng viên:")
                val teacherName = formatString()
                if (teacherName.isBlank()) {
                    println("Lỗi: Tên giảng viên không được để trống")
                    return inputEnrollmentInfo()
                }
                println("Nhập tuổi giảng viên (hoặc để trống):")
                val teacherAge = readLine()?.toIntOrNull()?.takeIf { it > 0 }
                val newTeacher = Teacher(Teacher.getAutoId(), teacherName, teacherAge)
                Repos.teacherRepo.add(newTeacher)
                FileUtils.saveTeachers(Repos.teacherRepo.all())
                newTeacher
            }
            clazz = Class(classId, subjectId, teacher.id)
            Repos.classRepo.add(clazz)
            FileUtils.saveClasses(Repos.classRepo.all())
            teacher.listClassIds.add(classId)
            FileUtils.saveTeachers(Repos.teacherRepo.all())
        } else if(clazz.subjectId != subjectId){
            println("Lỗi: Lớp $classId đã được đăng ký cho môn học khác (${clazz.subjectId})")
            return inputEnrollmentInfo()
        } else {
            println("Lớp đã tồn tại: $classId")
        }

        println("Điểm môn học:")
        val grade = readLine()?.toDoubleOrNull()?.takeIf { it in 0.0..10.0 } ?: run {
            println("Lỗi: Điểm phải từ 0 đến 10")
            return inputEnrollmentInfo()
        }

        return Enrollment(classId, grade)
    }

    fun addSubject(student: Student): Outcome {
        println("Thêm môn học")
        val enrollment = inputEnrollmentInfo()
        val subjectId = Repos.classRepo.findById(enrollment.classId)?.subjectId ?: return Outcome.Failure("Lỗi hệ thống")
        if (student.listEnrollments.any { Repos.classRepo.findById(it.classId)?.subjectId == subjectId }) {
            return Outcome.Failure("Môn học này đã tồn tại cho sinh viên")
        }
        student.listEnrollments.add(enrollment)
        FileUtils.saveStudents(Repos.studentRepo.all())
        return Outcome.Success("Thêm môn học thành công")
    }

    fun deleteSubject(student: Student): Outcome {
        println("Xóa môn học")
        val subjectId = readLine()?.takeIf { it.isNotBlank() } ?: return Outcome.Failure("Lỗi: Mã lớp không được để trống")
        val enrollment = student.listEnrollments.find { Repos.classRepo.findById(it.classId)?.subjectId == subjectId} ?: return Outcome.Failure("Môn học với mã $subjectId không tồn tại cho sinh viên")
        student.listEnrollments.remove(enrollment)
        FileUtils.saveStudents(Repos.studentRepo.all())
        return Outcome.Success("Xóa môn học thành công")
    }

    fun editSubject(student: Student): Outcome {
        println("Sửa môn học - Nhập môn học cần sửa:")
        val subjectId = readLine()?.takeIf { it.isNotBlank() } ?: return Outcome.Failure("Lỗi: Mã lớp không được để trống")
        val enrollment = student.listEnrollments.find { Repos.classRepo.findById(it.classId)?.subjectId == subjectId} ?: return Outcome.Failure("Môn học với mã $subjectId không tồn tại cho sinh viên")

        println("Sửa môn học - Nhập thông tin môn học:")
        println("Tên môn học (để trống để giữ nguyên):")
        val subjectName = readLine()?.takeIf { it.isNotBlank() }
        println("Điểm môn học:")
        val grade = readLine()?.toDoubleOrNull()?.takeIf { it in 0.0..10.0 } ?: return Outcome.Failure("Lỗi: Điểm phải từ 0 đến 10")
        println("Số tín chỉ (để trống để giữ nguyên):")
        val credits = readLine()?.toIntOrNull()?.takeIf { it > 0 }
        val clazz = Repos.classRepo.findById(enrollment.classId) ?: return Outcome.Failure("Lỗi hệ thống")
        val subject = Repos.subjectRepo.findById(clazz.subjectId) ?: return Outcome.Failure("Lỗi hệ thống")

        subjectName?.let {
            subject.name = it
        }
        credits?.let {
            subject.credits = it
        }
        enrollment.grade = grade

        println("Nhập mã giảng viên phụ trách (hoặc để trống nếu không thay đổi, nhập 0 để thêm mới):")
        val teacherId = readLine()?.toIntOrNull()
        val teacher = when (teacherId){
            null -> Repos.teacherRepo.findById(clazz.teacherId)

            0 -> {
                println("Thêm giảng viên mới - Nhập tên giảng viên: ")
                val teacherName = readLine()?.takeIf { it.isNotBlank() } ?: run {
                    println("Lỗi: Tên giảng viên không được để trống")
                    return Outcome.Failure("Vui lòng nhập lại")
                }
                println("Nhập tuổi giảng viên (hoặc để trống):")
                val teacherAge = readLine()?.toIntOrNull()?.takeIf { it > 0 }
                val newTeacher = Teacher(Teacher.getAutoId(), teacherName, teacherAge)
                Repos.teacherRepo.add(newTeacher)
                FileUtils.saveTeachers(Repos.teacherRepo.all())
                newTeacher
            }

            else -> {
                Repos.teacherRepo.findById(teacherId) ?: return Outcome.Failure("Không tìm thấy giảng viên có mã $teacherId")
            }
        } ?: return Outcome.Failure("Lỗi hệ thống")

        if(teacher.id != clazz.teacherId){
            val oldTeacher = Repos.teacherRepo.findById(clazz.teacherId)
            oldTeacher?.listClassIds?.remove(clazz.id)
            teacher.listClassIds.add(clazz.id)
            clazz.teacherId = teacher.id
            FileUtils.saveClasses(Repos.classRepo.all())
            FileUtils.saveTeachers(Repos.teacherRepo.all())
        }
        FileUtils.saveSubjects(Repos.subjectRepo.all())
        FileUtils.saveStudents(Repos.studentRepo.all())
        return Outcome.Success("Chỉnh sửa môn học thành công")
    }


}