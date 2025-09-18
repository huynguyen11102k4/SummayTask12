package utils

sealed class KetQua {
    data class ThanhCong(val giaTri: String) : KetQua()
    data class ThatBai(val loiNhan: String) : KetQua()
    object KhongXacDinh : KetQua()
}