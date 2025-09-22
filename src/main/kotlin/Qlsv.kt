import data.TeacherRepo
import utils.Outcome
import data.Subject
import data.StudentRepo
import extension.customSort
import extension.print
import extension.top10
import person.Teacher
import person.Student
import utils.Sort

fun main() {
    println("Quản lý sinh viên - Summary Task 12")
    val menu = listOf(
        "1. THÊM SINH VIÊN",
        "2. HIỂN THỊ DANH SÁCH",
        "3. TÌM KIẾM SINH VIÊN",
        "4. XÓA SINH VIÊN",
        "5. TOP 10 SINH VIÊN XUẤC SẮC",
        "6. CHỈNH SỬA THÔNG TIN SINH VIÊN",
        "7. SẮP XẾP DANH SÁCH SINH VIÊN"
    ).joinToString("\n")
    println(menu)

    while (true) {
        println("Nhập lựa chọn của bạn (1-7), nhập khác để thoát:")
        val choice = readInt("")
        val result: Outcome = when (choice) {
            1 -> addStudent()
            2 -> printStudentInfo()
            3 -> findStudent()
            4 -> deleteStudent()
            5 -> printTop10()
            6 -> editStudent()
            7 -> sortStudents()
            else -> {
                println("Thoát chương trình - Cảm ơn bạn đã sử dụng chương trình!")
                break
            }
        }
        result.print()
    }
}

fun readInt(msg: String): Int {
    while (true) {
        println("Input: $msg")
        val input = readLine()
        try {
            if (input != null && input.toIntOrNull() != null) {
                return input.toInt()
            }
        } catch (e: NumberFormatException) {
            println(e.message)
            println("Lỗi: Vui lòng nhập số nguyên hợp lệ")
        }
    }
}

fun addStudent(): Outcome {
    println("Thêm sinh viên - Nhập thông tin sinh viên:")
    println("Bước 1: Nhập mã sinh viên (hoặc để trống để tự động tạo mã):")
    val studentId = readLine()?.toInt() ?: Student.getAutoId()
    if (StudentRepo.findById(studentId) != null) {
        println("Lỗi: Mã sinh viên $studentId đã tồn tại")
        return Outcome.Failure("Vui lòng nhập lại")
    }

    println("Bước 2: Nhập tên sinh viên:")
    val name = readLine() ?: run {
        println("Lỗi: Tên không được để trống")
        return Outcome.Failure("Vui lòng nhập lại")
    }

    println("Bước 3: Nhập tuổi sinh viên:")
    val age = readLine()?.toInt()

    println("Bước 4: Nhập số môn học:")
    val numSubjects = readLine()?.toInt() ?: 0
    val subjects = mutableSetOf<Subject>()
    repeat(numSubjects) {
        subjects.add(inputSubjectInfo())
    }

    val student = Student(studentId, name, age, subjects)
    StudentRepo.addStudent(student)
    return Outcome.Success("Thêm sinh viên thành công")
}

fun printStudentInfo(): Outcome {
    StudentRepo.printAll()
    return Outcome.Success("In danh sách sinh viên thành công")
}

fun findStudent(): Outcome {
    val id = readInt("Nhập mã sinh viên cần tìm:")
    val student = StudentRepo.findById(id)
    return if (student != null) {
        student.printAll()
        Outcome.Success("Tìm thấy sinh viên có mã $id")
    } else {
        Outcome.Failure("Không tìm thấy sinh viên có mã $id")
    }
}

fun deleteStudent(): Outcome {
    val id = readInt("Nhập mã sinh viên cần xóa:")
    return if (StudentRepo.removeById(id)) {
        Outcome.Success("Xóa sinh viên thành công sinh viên có mã $id")
    } else {
        Outcome.Failure("Không tìm thấy sinh viên có mã $id")
    }
}

fun printTop10(): Outcome {
    val students = StudentRepo.all()
    students.top10().forEach { it.printAll() }
    return Outcome.Success("Trên đây là top 10 sinh viên có điểm trung bình cao nhất")
}

fun sortOption(choice: Int, ascending: Boolean): Outcome {
    return when (choice) {
        1 -> {
            Sort.ById(ascending).customSort()
            Outcome.Success("Sắp xếp sinh viên theo mã thành công")
        }

        2 -> {
            Sort.ByName(ascending).customSort()
            Outcome.Success("Sắp xếp sinh viên theo tên thành công")
        }

        3 -> {
            Sort.ByScore(ascending).customSort()
            Outcome.Success("Sắp xếp sinh viên theo điểm trung bình thành công")
        }

        else -> Outcome.Failure("Lựa chọn không hợp lệ")
    }
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

fun inputSubjectInfo(): Subject {
    println("Nhập thông tin môn học")
    println("Mã môn học:")
    val subjectId = readLine()
    println("Tên môn học:")
    val subjectName = readLine()
    println("Điểm môn học:")
    val grade = readLine()!!.toDouble()
    println("Số tín chỉ:")
    val credits = readLine()!!.toInt()
    println("Nhập mã giảng viên phụ trách môn học (hoặc để trống để thêm mới):")
    val teacherId = readLine()?.toIntOrNull()
    val teacher = teacherId?.let { TeacherRepo.findById(it) } ?: run {
        println("Thêm giảng viên mới - Nhập tên giảng viên:")
        val teacherName = readLine() ?: run {
            println("Lỗi: Tên giảng viên không được để trống")
            return inputSubjectInfo()
        }
        val teacherAge = readLine()?.toIntOrNull()
        val newTeacher = Teacher(Teacher.getAutoId(), teacherName, teacherAge)
        TeacherRepo.addTeacher(newTeacher)
        newTeacher
    }
    return Subject(subjectId, subjectName.toString(), grade, credits, teacher)
}

fun addSubject(student: Student): Outcome {
    println("Thêm môn học")
    val subject = inputSubjectInfo()
    student.listSubjects.add(subject)
    subject.teacher?.listSubjects?.add(subject)
    return Outcome.Success("Thêm môn học thành công")
}

fun deleteSubject(student: Student): Outcome {
    println("Xóa môn học - Nhập mã môn học cần xóa:")
    val subjectId = readLine()
    val subject = student.listSubjects.find { it.id == subjectId }
    if (subject != null) {
        student.listSubjects.remove(subject)
        subject.teacher?.listSubjects?.remove(subject)
        return Outcome.Success("Xóa môn học thành công")
    } else {
        return Outcome.Failure("Không tìm thấy môn học có mã $subjectId")
    }
}

fun editSubject(student: Student): Outcome {
    println("Sửa môn học - Nhập mã môn học cần sửa:")
    val subjectId = readLine()
    val subject = student.listSubjects.find { it.id == subjectId }
    if (subject == null) {
        return Outcome.Failure("Không tìm thấy môn học có mã $subjectId")
    }

    println("Sửa môn học - Nhập thông tin môn học:")
    println("Tên môn học:")
    val subjectName = readLine()
    println("Điểm môn học:")
    val grade = readLine()?.toDouble()
    println("Số tín chỉ:")
    val credits = readLine()?.toInt()

    println("Nhập mã giảng viên phụ trách (hoặc để trống nếu không thay đổi, nhập 0 để thêm mới):")
    val teacherId = readLine()?.toIntOrNull()
    val teacher = when (teacherId) {
        null -> subject.teacher
        0 -> {
            println("Thêm giảng viên mới - Nhập tên giảng viên:")
            val teacherName = readLine() ?: run {
                println("Lỗi: Tên giảng viên không được để trống")
                return Outcome.Failure("Vui lòng nhập lại")
            }
            println("Nhập tuổi giảng viên (hoặc để trống):")
            val teacherAge = readLine()?.toIntOrNull()
            val newTeacher = Teacher(Teacher.getAutoId(), teacherName, teacherAge)
            TeacherRepo.addTeacher(newTeacher)
            newTeacher
        }

        else -> {
            TeacherRepo.findById(teacherId) ?: return Outcome.Failure("Không tìm thấy giảng viên có mã $teacherId")
        }
    }

    subjectName?.let { subject.name = it }
    grade?.let { subject.grade = it }
    credits?.let { subject.credits = it }
    teacher?.let {
        subject.teacher?.listSubjects?.remove(subject)
        subject.teacher = it
        it.listSubjects.add(subject)
    }

    return Outcome.Success("Chỉnh sửa môn học thành công")
}

fun editStudent(): Outcome {
    val id = readInt("Nhập mã sinh viên cần chỉnh sửa:")
    val student = StudentRepo.findById(id) ?: return Outcome.Failure("Không tìm thấy sinh viên có mã $id")

    println("Chỉnh sửa sinh viên - Lựa chọn thông tin cần chỉnh sửa:")
    val menu = listOf(
        "1. Tên", "2. Tuổi", "3. Thêm môn học", "4. Xóa môn học", "5. Chỉnh sửa môn học"
    ).joinToString("\n")
    println(menu)

    while (true) {
        val choice = readInt("Nhập lựa chọn của bạn (1-5), nhập khác để thoát:")
        val result = when (choice) {
            1 -> {
                print("Nhập tên mới:")
                val newName = readLine() ?: run {
                    println("Lỗi: Tên không được để trống")
                    Outcome.Failure("Vui lòng nhập lại")
                }
                student.name = newName.toString()
                Outcome.Success("Chỉnh sửa tên thành công")
            }

            2 -> {
                print("Nhập tuổi mới:")
                val newAge = readLine()?.toInt()
                student.age = newAge
                Outcome.Success("Chỉnh sửa tuổi thành công")
            }

            3 -> addSubject(student)
            4 -> deleteSubject(student)
            5 -> editSubject(student)
            else -> {
                println("Thoát chỉnh sửa sinh viên")
                Outcome.Unknown
                break
            }
        }
        result.print()
    }
    return Outcome.Success("Kết thúc chỉnh sửa sinh viên có mã $id")
}
