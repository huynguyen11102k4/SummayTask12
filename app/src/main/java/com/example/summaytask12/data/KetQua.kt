package com.example.summaytask12.data

import android.util.Log


sealed class KetQua {
    data class ThanhCong(val giaTri: String) : KetQua()
    data class ThatBai(val loiNhan: String) : KetQua()
    object KhongXacDinh : KetQua()
}

fun KetQua.inRa() {
    when (this) {
        is KetQua.ThanhCong -> Log.d("KetQua", giaTri)
        is KetQua.ThatBai -> Log.d("KetQua", loiNhan)
        KetQua.KhongXacDinh -> Log.e("KetQua", "Kết quả không xác định")
    }
}