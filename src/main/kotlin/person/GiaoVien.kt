package person

import data.MonHoc

class GiaoVien(
    override var ma: Int,
    override var ten: String,
    override var tuoi: Int? = null,
    val dsMonHoc: MutableSet<MonHoc> = mutableSetOf()
) : Nguoi(ma, ten, tuoi) {

    constructor(ma: Int, ten: String) : this(ma, ten, null)

    override fun xuatThongTin() {
        println("Mã giảng viên: $ma")
        println("Họ tên: $ten")
        println("Tuổi: ${tuoi ?: "Chưa cung cấp"}")
        if (dsMonHoc.isEmpty()) {
            println("Giảng viên $ten - $ma: Chưa phụ trách môn học nào")
        } else {
            println("Giảng viên $ten - $ma: Danh sách môn học phụ trách:")
            dsMonHoc.forEachIndexed { i, mh ->
                println("Môn ${i + 1}: Tên: ${mh.ten}, Mã: ${mh.ma}, Số tín chỉ: ${mh.soTinChi}")
            }
        }
        println("--------------------------------------------------")
    }

    companion object {
        private var AUTO_ID = 2000
        fun getAutoId(): Int {
            return AUTO_ID++
        }
    }
}