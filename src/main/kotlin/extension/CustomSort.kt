package extension

import data.StudentRepo
import person.Student
import utils.Sort

fun Sort.customSort() {
    when (this) {
        is Sort.ById -> {
            if (ascending) {
                StudentRepo.all().sortedBy { it.id }
            } else {
                StudentRepo.all().sortedBy { it.id }.reversed()
            }
        }
        is Sort.ByName -> {
            if (ascending) {
                StudentRepo.all().sortedByNameRightToLeft(true)
            } else{
                StudentRepo.all().sortedByNameRightToLeft(false)
            }
        }
        is Sort.ByScore -> {
            if(ascending) {
                StudentRepo.all().sortedBy { it.calculateAverageScore() }
            } else {
                StudentRepo.all().sortedBy { it.calculateAverageScore() }.reversed()
            }
        }
        Sort.Unspecified -> {
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

