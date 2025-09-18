package utils

sealed class SapXep {
    data class TheoMa(val tangDan: Boolean) : SapXep()
    data class TheoTen(val tangDan: Boolean) : SapXep()
    data class TheoDiem(val tangDan: Boolean) : SapXep()
    object KhongXacDinh : SapXep()
}
