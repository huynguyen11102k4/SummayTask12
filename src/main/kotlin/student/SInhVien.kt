package student

import data.MonHoc

class SinhVien(
    override var ma: Int,
    override var ten: String,
    override var tuoi: Int? = null,
    val dsMonHoc: MutableSet<MonHoc> = mutableSetOf()
) : Nguoi(ma, ten, tuoi), DiemTB {

    constructor(ma: Int, ten: String) : this(ma, ten, null)

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
            println("Sinh viên $ten - $ma: Chưa có môn học")
        } else {
            println("Sinh viên $ten - $ma: Danh sách môn học:")
            dsMonHoc.forEachIndexed { i, mh ->
                println("Môn ${i + 1}: Tên: ${mh.ten}, Điểm: ${mh.diem}, Số tín chỉ: ${mh.soTinChi}")
            }
        }
        println("Điểm trung bình: ${tinhDiemTB()}")
        println("--------------------------------------------------")
    }

    companion object {
        private var AUTO_ID = 1000
        fun getAutoId(): Int {
            return AUTO_ID++
        }
    }
}