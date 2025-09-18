package extension

import data.StudentRepo
import person.SinhVien
import utils.SapXep

fun SapXep.customSort() {
    when (this) {
        is SapXep.TheoMa -> {
            if (tangDan) {
                StudentRepo.getAllSV().sortedBy { it.ma }
            } else {
                StudentRepo.getAllSV().sortedBy { it.ma }.reversed()
            }
        }
        is SapXep.TheoTen -> {
            if (tangDan) {
                StudentRepo.getAllSV().sortedByNameRightToLeft(true)
            } else{
                StudentRepo.getAllSV().sortedByNameRightToLeft(false)
            }
        }
        is SapXep.TheoDiem -> {
            if(tangDan) {
                StudentRepo.getAllSV().sortedBy { it.tinhDiemTB() }
            } else {
                StudentRepo.getAllSV().sortedBy { it.tinhDiemTB() }.reversed()
            }
        }
        SapXep.KhongXacDinh -> {
        }
    }
}

fun MutableList<SinhVien>.sortedByNameRightToLeft(tangDan: Boolean = true) {
    val cmp = Comparator<SinhVien>{ a, b ->
        val tenA = a.ten.trim().split(Regex("\\s+")).asReversed()
        val tenB = b.ten.trim().split(Regex("\\s+")).asReversed()

        val n = minOf(tenA.size, tenB.size)
        for (i in 0 until n) {
            val res = tenA[i].compareTo(tenB[i], ignoreCase = true)
            if (res != 0) {
                return@Comparator res
            }
        }
        tenA.size.compareTo(tenB.size)
    }
    return if (tangDan) {
        this.sortWith(cmp)
    } else {
        this.sortWith(cmp.reversed())
    }
}

