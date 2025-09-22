package extension

import person.Student

fun List<Student>.top10(): List<Student> {
    return this.sortedByDescending { it.calculateAverageScore() }.take(10)
}