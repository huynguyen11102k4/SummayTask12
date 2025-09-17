package data

import student.SinhVien


object StudentRepo {
    private val dsSV = mutableSetOf<SinhVien>()

    fun themSV(sv: SinhVien) {
        dsSV.add(sv)
    }
    fun inDS() {
        for (sv in dsSV) {
            sv.xuatThongTin()
        }
    }
    fun timSV(ma: Int): SinhVien? {
        return dsSV.find { it.ma == ma }
    }
    fun xoaSV(ma: Int): Boolean {
        val sv = timSV(ma)
        return if (sv != null) {
            dsSV.remove(sv)
            true
        } else {
            false
        }
    }

    fun getAllSV(): Set<SinhVien> {
        return dsSV.toSet()
    }
}