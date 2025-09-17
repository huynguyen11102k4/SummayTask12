package data

sealed class KetQua {
    data class ThanhCong(val giaTri: String) : KetQua()
    data class ThatBai(val loiNhan: String) : KetQua()
    object KhongXacDinh : KetQua()
}

fun KetQua.inRa() {
    when (this) {
        is KetQua.ThanhCong -> println("KetQua: $giaTri")
        is KetQua.ThatBai -> println("KetQua: $loiNhan")
        KetQua.KhongXacDinh -> println("KetQua: Kết quả không xác định")
    }
}