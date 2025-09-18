import data.GiaoVienRepo
import utils.KetQua
import data.MonHoc
import data.StudentRepo
import extension.customSort
import extension.inRa
import extension.top10
import person.GiaoVien
import person.SinhVien
import utils.SapXep

fun main() {
    println("Quản lý sinh viên - Summary Task 12")
    val menu = listOf(
        "1. THÊM SINH VIÊN",
        "2. HIỂN THỊ DANH SÁCH",
        "3. TÌM KIẾM SINH VIÊN",
        "4. XÓA SINH VIÊN",
        "5. TOP 10 SINH VIÊN XUẤT SẮC",
        "6. CHỈNH SỬA THÔNG TIN SINH VIÊN",
        "7. SẮP XẾP DANH SÁCH SINH VIÊN"
    ).joinToString("\n")
    println(menu)

    while (true) {
        println("Nhập lựa chọn của bạn (1-7), nhập khác để thoát:")
        val lc = readInt("")
        val kq: KetQua = when (lc) {
            1 -> themSV()
            2 -> inThongTinSV()
            3 -> timKiemSV()
            4 -> xoaSV()
            5 -> inTop10()
            6 -> chinhSuaSV()
            7 -> sapXepSV()
            else -> {
                println("Thoát chương trình - Cảm ơn bạn đã sử dụng chương trình!")
                break
            }
        }
        kq.inRa()
    }
}

fun readInt(p: String): Int {
    while (true) {
        println("Input: $p")
        val input = readLine()
        try {
            if (input != null && input.toIntOrNull() != null) {
                return input.toInt()
            }
        } catch (e: NumberFormatException) {
            println(e.message)
            println("Lỗi: Vui lòng nhập số nguyên hợp lệ")
        }
    }
}

fun themSV(): KetQua {
    println("Thêm sinh viên - Nhập thông tin sinh viên:")
    println("Bước 1: Nhập mã sinh viên (hoặc để trống để tự động tạo mã):")
    val maSV = readLine()?.toInt() ?: SinhVien.getAutoId()
    if (StudentRepo.timSV(maSV) != null) {
        println("Lỗi: Mã sinh viên $maSV đã tồn tại")
        return KetQua.ThatBai("Vui lòng nhập lại")
    }

    println("Bước 2: Nhập tên sinh viên:")
    val ten = readLine() ?: run {
        println("Lỗi: Tên không được để trống")
        return KetQua.ThatBai("Vui lòng nhập lại")
    }

    println("Bước 3: Nhập tuổi sinh viên:")
    val tuoi = readLine()?.toInt()

    println("Bước 4: Nhập số môn học:")
    val soMonHoc = readLine()?.toInt() ?: 0
    val dsMonHoc = mutableSetOf<MonHoc>()
    repeat(soMonHoc) {
        dsMonHoc.add(nhapThongTinMonHoc())
    }

    val sv = SinhVien(maSV, ten, tuoi, dsMonHoc)
    StudentRepo.themSV(sv)
    return KetQua.ThanhCong("Thêm sinh viên thành công")
}

fun inThongTinSV(): KetQua {
    StudentRepo.inDS()
    return KetQua.ThanhCong("In danh sách sinh viên thành công")
}

fun timKiemSV(): KetQua {
    val ma = readInt("Nhập mã sinh viên cần tìm:")
    val sv = StudentRepo.timSV(ma)
    return if (sv != null) {
        sv.xuatThongTin()
        KetQua.ThanhCong("Tìm thấy sinh viên có mã $ma")
    } else {
        KetQua.ThatBai("Không tìm thấy sinh viên có mã $ma")
    }
}

fun xoaSV(): KetQua {
    val ma = readInt("Nhập mã sinh viên cần xóa:")
    return if (StudentRepo.xoaSV(ma)) {
        KetQua.ThanhCong("Xóa sinh viên thành công sinh viên có mã $ma")
    } else {
        KetQua.ThatBai("Không tìm thấy sinh viên có mã $ma")
    }
}

fun inTop10(): KetQua {
    val dsSV = StudentRepo.getAllSV()
    dsSV.top10().forEach { it.xuatThongTin() }
    return KetQua.ThanhCong("Trên đây là top 10 sinh viên có điểm trung bình cao nhất")
}

fun luaChonSapXep(lc: Int, lc1: Boolean): KetQua {
    return when (lc) {
        1 -> {
            SapXep.TheoMa(lc1).customSort()
            KetQua.ThanhCong("Sắp xếp sinh viên theo mã thành công")
        }

        2 -> {
            SapXep.TheoTen(lc1).customSort()
            KetQua.ThanhCong("Sắp xếp sinh viên theo tên thành công")
        }

        3 -> {
            SapXep.TheoDiem(lc1).customSort()
            KetQua.ThanhCong("Sắp xếp sinh viên theo điểm trung bình thành công")
        }

        else -> KetQua.ThatBai("Lựa chọn không hợp lệ")
    }
}

fun sapXepSV(): KetQua {
    println("Sắp xếp sinh viên theo: 1. Mã   2. Tên     3. Điểm trung bình")
    val lc = readInt("Nhập lựa chọn của bạn (1-3), nhập khác để thoát:")
    if (lc < 1 || lc > 3) {
        return KetQua.ThatBai("Lựa chọn không hợp lệ")
    }
    println("a. Tăng dần   b. Giảm dần")
    val lc1 = readLine()

    return if (lc1 != "a" && lc1 != "b") {
        KetQua.ThatBai("Lựa chọn không hợp lệ")
    } else if (lc1 == "a") {
        luaChonSapXep(lc, true)
    } else {
        luaChonSapXep(lc, false)
    }
}

fun nhapThongTinMonHoc(): MonHoc {
    println("Nhập thông tin môn học")
    println("Mã môn học:")
    val maMonHoc = readLine()
    println("Tên môn học:")
    val tenMonHoc = readLine()
    println("Điểm môn học:")
    val diemMonHoc = readLine()!!.toDouble()
    println("Số tín chỉ:")
    val soTinChi = readLine()!!.toInt()
    println("Nhập mã giảng viên phụ trách môn học (hoặc để trống để thêm mới):")
    val maGV = readLine()?.toIntOrNull()
    val giaoVien = maGV?.let { GiaoVienRepo.timGV(it) } ?: run {
        println("Thêm giảng viên mới - Nhập tên giảng viên:")
        val tenGV = readLine() ?: run {
            println("Lỗi: Tên giảng viên không được để trống")
            return nhapThongTinMonHoc()
        }
        val tuoiGV = readLine()?.toIntOrNull()
        val gv = GiaoVien(GiaoVien.getAutoId(), tenGV, tuoiGV)
        GiaoVienRepo.themGV(gv)
        gv
    }
    return MonHoc(maMonHoc, tenMonHoc.toString(), diemMonHoc, soTinChi, giaoVien)
}

fun themMonHoc(sv: SinhVien): KetQua {
    println("Thêm môn học")
    val mh = nhapThongTinMonHoc()
    sv.dsMonHoc.add(mh)
    mh.giaoVien?.dsMonHoc?.add(mh)
    return KetQua.ThanhCong("Thêm môn học thành công")
}

fun xoaMonHoc(sv: SinhVien): KetQua {
    println("Xóa môn học - Nhập mã môn học cần xóa:")
    val maMonHoc = readLine()
    val mh = sv.dsMonHoc.find { it.ma == maMonHoc }
    if (mh != null) {
        sv.dsMonHoc.remove(mh)
        mh.giaoVien?.dsMonHoc?.remove(mh)
        return KetQua.ThanhCong("Xóa môn học thành công")
    } else {
        return KetQua.ThatBai("Không tìm thấy môn học có mã $maMonHoc")
    }
}

fun chinhSuaMonHoc(sv: SinhVien): KetQua {
    println("Sửa môn học - Nhập mã môn học cần sửa:")
    val maMonHoc = readLine()
    val mh = sv.dsMonHoc.find { it.ma == maMonHoc }
    if (mh == null) {
        return KetQua.ThatBai("Không tìm thấy môn học có mã $maMonHoc")
    }

    println("Sửa môn học - Nhập thông tin môn học:")
    println("Tên môn học:")
    val tenMonHoc = readLine()
    println("Điểm môn học:")
    val diemMonHoc = readLine()?.toDouble()
    println("Số tín chỉ:")
    val soTinChi = readLine()?.toInt()

    println("Nhập mã giảng viên phụ trách (hoặc để trống nếu không thay đổi, nhập 0 để thêm mới):")
    val maGV = readLine()?.toIntOrNull()
    val giaoVien = when (maGV) {
        null -> mh.giaoVien
        0 -> {
            println("Thêm giảng viên mới - Nhập tên giảng viên:")
            val tenGV = readLine() ?: run {
                println("Lỗi: Tên giảng viên không được để trống")
                return KetQua.ThatBai("Vui lòng nhập lại")
            }
            println("Nhập tuổi giảng viên (hoặc để trống):")
            val tuoiGV = readLine()?.toIntOrNull()
            val gv = GiaoVien(GiaoVien.getAutoId(), tenGV, tuoiGV)
            GiaoVienRepo.themGV(gv)
            gv
        }

        else -> {
            GiaoVienRepo.timGV(maGV) ?: return KetQua.ThatBai("Không tìm thấy giảng viên có mã $maGV")
        }
    }

    tenMonHoc?.let { mh.ten = it }
    diemMonHoc?.let { mh.diem = it }
    soTinChi?.let { mh.soTinChi = it }
    giaoVien?.let {
        mh.giaoVien?.dsMonHoc?.remove(mh)
        mh.giaoVien = it
        it.dsMonHoc.add(mh)
    }

    return KetQua.ThanhCong("Chỉnh sửa môn học thành công")
}

fun chinhSuaSV(): KetQua {
    val ma = readInt("Nhập mã sinh viên cần chỉnh sửa:")
    val sv = StudentRepo.timSV(ma) ?: return KetQua.ThatBai("Không tìm thấy sinh viên có mã $ma")

    println("Chỉnh sửa sinh viên - Lựa chọn thông tin cần chỉnh sửa:")
    val menu = listOf(
        "1. Tên", "2. Tuổi", "3. Thêm môn học", "4. Xóa môn học", "5. Chỉnh sửa môn học"
    ).joinToString("\n")
    println(menu)

    while (true) {
        val lc = readInt("Nhập lựa chọn của bạn (1-5), nhập khác để thoát:")
        val kq = when (lc) {
            1 -> {
                print("Nhập tên mới:")
                val ten = readLine() ?: run {
                    println("Lỗi: Tên không được để trống")
                    KetQua.ThatBai("Vui lòng nhập lại")
                }
                sv.ten = ten.toString()
                KetQua.ThanhCong("Chỉnh sửa tên thành công")
            }

            2 -> {
                print("Nhập tuổi mới:")
                val tuoi = readLine()?.toInt()
                sv.tuoi = tuoi
                KetQua.ThanhCong("Chỉnh sửa tuổi thành công")
            }

            3 -> themMonHoc(sv)
            4 -> xoaMonHoc(sv)
            5 -> chinhSuaMonHoc(sv)
            else -> {
                println("Thoát chỉnh sửa sinh viên")
                KetQua.KhongXacDinh
                break
            }
        }
        kq.inRa()
    }
    return KetQua.ThanhCong("Kết thúc chỉnh sửa sinh viên có mã $ma")
}