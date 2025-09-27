package utils

import data.Subject
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import person.Student
import person.Teacher
import java.io.File

object FileUtils {
    val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    fun saveStudents(students: List<Student>, filePath: String = "students.json"){
        val jsonData = json.encodeToString(students)
        File(filePath).writeText(jsonData)
    }

    fun loadStudents(filePath: String = "students.json") : List<Student> {
        val file = File(filePath)
        if (!file.exists()) return emptyList()
        val jsonData = file.readText()
        return json.decodeFromString(jsonData)
    }

    fun saveTeachers(teachers: List<Teacher>, filePath: String = "teachers.json") {
        val jsonData = json.encodeToString(teachers)
        File(filePath).writeText(jsonData)
    }

    fun loadTeachers(filePath: String = "teachers.json"): List<Teacher> {
        val file = File(filePath)
        if (!file.exists()) return emptyList()
        val jsonData = file.readText()
        return json.decodeFromString(jsonData)
    }

    fun saveSubjects(subjects: List<Subject>, filePath: String = "subjects.json") {
        val jsonData = json.encodeToString(subjects)
        File(filePath).writeText(jsonData)
    }

    fun loadSubjects(filePath: String = "subjects.json"): List<Subject> {
        val file = File(filePath)
        if (!file.exists()) return emptyList()
        val jsonData = file.readText()
        return json.decodeFromString(jsonData)
    }

    fun saveClasses(classes: List<data.Class>, filePath: String = "classes.json") {
        val jsonData = json.encodeToString(classes)
        File(filePath).writeText(jsonData)
    }

    fun loadClasses(filePath: String = "classes.json"): List<data.Class> {
        val file = File(filePath)
        if (!file.exists()) return emptyList()
        val jsonData = file.readText()
        return json.decodeFromString(jsonData)
    }
}