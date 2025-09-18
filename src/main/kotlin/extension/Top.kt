package extension

import person.SinhVien

fun List<SinhVien>.top10(): List<SinhVien> {
    return this.sortedByDescending { it.tinhDiemTB() }.take(10)
}