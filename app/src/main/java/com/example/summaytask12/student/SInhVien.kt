package com.example.summaytask12.student

import android.annotation.SuppressLint
import android.util.Log
import com.example.summaytask12.student.DiemTB
import com.example.summaytask12.data.MonHoc

class SinhVien(
    override var ma: Int,
    override var ten: String,
    override var tuoi: Int? = null,
    val dsMonHoc: MutableSet<MonHoc> = mutableSetOf()
) : Nguoi(ma, ten, tuoi), DiemTB {

    constructor(ma: Int, ten: String) : this(ma, ten, null)

    @SuppressLint("DefaultLocale") // Để sử dụng String.format với định dạng mặc định
    override fun tinhDiemTB(): Double {
        if (dsMonHoc.isEmpty()) {
            return 0.0
        }
        val tongTinChi = dsMonHoc.sumOf { it.soTinChi }
        val tong = dsMonHoc.sumOf { it.diem * it.soTinChi }
        return (tong / tongTinChi).let { String.format("%.2f", it).toDouble() }
    }

    override fun xuatThongTin() {
        super.xuatThongTin()
        if (dsMonHoc.isEmpty()) {
            Log.d("Sinh viên $ten - $ma", "Chưa có môn học")
        } else {
            Log.d("Sinh viên $ten - $ma", "Danh sách môn học:")
            dsMonHoc.forEachIndexed { i, mh ->
                Log.d("Môn ${i + 1}", "Tên: ${mh.ten}, Điểm: ${mh.diem}, Số tín chỉ: ${mh.soTinChi}")
            }
        }
        Log.d("Điểm trung bình", "${tinhDiemTB()}")
        Log.d("--------------------------------------------------", "--------------------------------------------------")
    }

    companion object {
        private var AUTO_ID = 1000
        fun getAutoId(): Int {
            return AUTO_ID++
        }
    }
}