package com.example.summaytask12.data

import com.example.summaytask12.student.SinhVien

object StudentRepo {
    private val dsSV = mutableListOf<SinhVien>()

    fun themSV(sv: SinhVien) {
        dsSV.add(sv)
    }
    fun inDS() {
        for (sv in dsSV) {
            sv.xuatThongTin()
        }
    }
    fun timSV(ma: Int): SinhVien? {
        for (sv in dsSV) {
            if (sv.ma == ma) {
                return sv
            }
        }
        return null
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

    fun getAllSV(): List<SinhVien> {
        return dsSV.toList()
    }
}