package extension

import repository.StudentRepo
import person.Student
import utils.Sort

fun Sort.customSort(repo: StudentRepo = StudentRepo()) : List<Student> {
    return when (this) {
        is Sort.ById -> {
            if (ascending) {
                repo.all().sortedBy { it.id }
            } else {
                repo.all().sortedBy { it.id }.reversed()
            }
        }
        is Sort.ByName -> {
            val mutableList = repo.all().toMutableList()
            mutableList.sortedByNameRightToLeft(ascending)
            mutableList
        }
        is Sort.ByScore -> {
            if(ascending) {
                repo.all().sortedBy { it.calculateAverageScore() }
            } else {
                repo.all().sortedBy { it.calculateAverageScore() }.reversed()
            }
        }
        Sort.Unspecified -> {
            repo.all()
        }
    }
}

fun MutableList<Student>.sortedByNameRightToLeft(increase: Boolean = true) {
    val cmp = Comparator<Student>{ a, b ->
        val nameA = a.name.trim().split(Regex("\\s+")).asReversed()
        val nameB = b.name.trim().split(Regex("\\s+")).asReversed()

        val n = minOf(nameA.size, nameB.size)
        for (i in 0 until n) {
            val res = nameA[i].compareTo(nameB[i], ignoreCase = true)
            if (res != 0) {
                return@Comparator res
            }
        }
        nameA.size.compareTo(nameB.size)
    }
    return if (increase) {
        this.sortWith(cmp)
    } else {
        this.sortWith(cmp.reversed())
    }
}

