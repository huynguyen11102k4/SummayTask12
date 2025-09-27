import utils.Outcome
import extension.print
import repository.Repos
import service.StudentService
import utils.FileUtils

fun main() {
    val studentService = StudentService()

    val loadStudents = FileUtils.loadStudents()
    val loadTeachers = FileUtils.loadTeachers()
    val loadSubjects = FileUtils.loadSubjects()
    val loadClasses = FileUtils.loadClasses()

    loadStudents.forEach {
        Repos.studentRepo.add(it)
    }
    loadTeachers.forEach {
        Repos.teacherRepo.add(it)
    }
    loadSubjects.forEach {
        Repos.subjectRepo.add(it)
    }
    loadClasses.forEach {
        Repos.classRepo.add(it)
    }

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
            1 -> studentService.addStudent()
            2 -> studentService.printStudentInfo()
            3 -> studentService.findStudent()
            4 -> studentService.deleteStudent()
            5 -> studentService.printTop10()
            6 -> studentService.editStudent()
            7 -> studentService.sortStudents()
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
        print(msg)
        val input = readLine()
        if (input != null) {
            val number = input.toIntOrNull()
            if (number != null) {
                return number
            } else {
                println("Lỗi: Vui lòng nhập một số nguyên hợp lệ.")
            }
        } else {
            println("Lỗi: Vui lòng nhập một số nguyên hợp lệ.")
        }
    }
}

fun formatString(): String {
    val str = readLine() ?: ""
    while (str.isBlank()) {
        println("Lỗi: Vui lòng nhập chuỗi không được để trống.")
        val str = readLine() ?: ""
        if (str.isNotBlank()) {
            break
        }
    }
    return str.trim().split("\\s+".toRegex())
        .joinToString(" ") { it.lowercase().replaceFirstChar { ch -> ch.uppercase() } }
}

