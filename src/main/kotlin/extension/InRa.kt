package extension

import utils.KetQua

fun KetQua.inRa() {
    when (this) {
        is KetQua.ThanhCong -> println("KetQua: $giaTri")
        is KetQua.ThatBai -> println("KetQua: $loiNhan")
        KetQua.KhongXacDinh -> println("KetQua: Kết quả không xác định")
    }
}