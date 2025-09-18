package data

import person.GiaoVien

object GiaoVienRepo {
    private val dsGV = mutableListOf<GiaoVien>()

    fun themGV(gv: GiaoVien) {
        dsGV.add(gv)
    }

    fun inDS() {
        if (dsGV.isEmpty()) {
            println("Danh sách giảng viên trống")
        } else {
            for (gv in dsGV) {
                gv.xuatThongTin()
            }
        }
    }

    fun timGV(ma: Int): GiaoVien? {
        return dsGV.find { it.ma == ma }
    }

    fun xoaGV(ma: Int): Boolean {
        val gv = timGV(ma)
        return if (gv != null) {
            dsGV.remove(gv)
            true
        } else {
            false
        }
    }

    fun getAllGV(): MutableList<GiaoVien> {
        return dsGV
    }
}