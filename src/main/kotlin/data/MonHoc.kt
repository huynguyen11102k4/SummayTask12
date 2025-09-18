package data

import person.GiaoVien

data class MonHoc(
    var ma: String? = null,
    var ten: String = "",
    var diem: Double = 0.0,
    var soTinChi: Int = 0,
    var giaoVien: GiaoVien? = null
)