import data.ChinhSuaSv
import data.KetQua
import data.LuaChon
import data.MonHoc
import data.StudentRepo
import data.inRa
import student.SinhVien

fun main() {
    println("Quản lý sinh viên - Summary Task 12")
    for (lc in LuaChon.entries) {
        println("Lựa chọn: ${lc.giaTri}. ${lc.name.replace('_', ' ')}")
    }
    while (true) {
        println("Nhập lựa chọn của bạn (1-${LuaChon.entries.size}), nhập khác để thoát:")
        val lc = readInt("")
        val kq: KetQua = when (lc) {
            1 -> themSV()
            2 -> inThongTinSV()
            3 -> timKiemSV()
            4 -> xoaSV()
            5 -> inTop10()
            6 -> {
                val ma = readInt("Nhập mã sinh viên cần chỉnh sửa:")
                chinhSuaSV(ma)
            }
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
            println("Lỗi: Vui lòng nhập số nguyên hợp lệ")
        }
    }
}

fun Set<SinhVien>.top10(): Set<SinhVien> {
    return this.sortedByDescending { it.tinhDiemTB() }.take(10).toSet()
}

fun themSV(): KetQua {
    println("Thêm sinh viên - Nhập thông tin sinh viên:")
    println("Bước 1: Nhập mã sinh viên (hoặc để trống để tự động tạo mã):")
    val maSV = readLine()?.toInt() ?: SinhVien.getAutoId()

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
    StudentRepo.timSV(ma)?.let {
        it.xuatThongTin()
        return KetQua.ThanhCong("Tìm sinh viên thành công sinh viên có mã $ma")
    } ?: return KetQua.ThatBai("Không tìm thấy sinh viên có mã $ma")
}

fun xoaSV(): KetQua {
    val ma = readInt("Nhập mã sinh viên cần xóa:")
    if (StudentRepo.xoaSV(ma)) {
        return KetQua.ThanhCong("Xóa sinh viên thành công sinh viên có mã $ma")
    } else {
        return KetQua.ThatBai("Không tìm thấy sinh viên có mã $ma")
    }
}

fun inTop10(): KetQua {
    val dsSV = StudentRepo.getAllSV()
    dsSV.top10().forEach { it.xuatThongTin() }
    return KetQua.ThanhCong("Trên đây là top 10 sinh viên có điểm trung bình cao nhất")
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
    return MonHoc(maMonHoc, tenMonHoc.toString(), diemMonHoc, soTinChi)
}

fun themMonHoc(sv: SinhVien): KetQua {
    println("Thêm môn học - Nhập thông tin môn học:")
    val maMonHoc = readLine()
    val tenMonHoc = readLine()
    val diemMonHoc = readLine()?.toDouble() ?: 0.0
    val soTinChi = readLine()?.toInt() ?: 0
    val mh = MonHoc(maMonHoc, tenMonHoc.toString(), diemMonHoc, soTinChi)
    sv.dsMonHoc.add(mh)
    return KetQua.ThanhCong("Thêm môn học thành công")
}

fun xoaMonHoc(sv: SinhVien): KetQua {
    println("Xóa môn học - Nhập mã môn học cần xóa:")
    val maMonHoc = readLine()
    val mh = sv.dsMonHoc.find { it.ma == maMonHoc }
    if (mh != null) {
        sv.dsMonHoc.remove(mh)
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

    tenMonHoc?.let { mh.ten = it }
    diemMonHoc?.let { mh.diem = it }
    soTinChi?.let { mh.soTinChi = it }

    return KetQua.ThanhCong("Chỉnh sửa môn học thành công")
}

fun chinhSuaSV(ma: Int): KetQua {
    val sv = StudentRepo.timSV(ma) ?: return KetQua.ThatBai("Không tìm thấy sinh viên có mã $ma")

    println("Chỉnh sửa sinh viên - Lựa chọn thông tin cần chỉnh sửa:")
    for (cs in ChinhSuaSv.entries) {
        println("Chỉnh sửa sinh viên: ${cs.giaTri}. ${cs.name.replace('_', ' ')}")
    }

    while (true) {
        val lc = readInt("Nhập lựa chọn của bạn (1-${ChinhSuaSv.entries.size}), nhập khác để thoát:")
        when (lc) {
            1 -> {
                val ten = readLine() ?: run {
                    println("Lỗi: Tên không được để trống")
                    return KetQua.ThatBai("Vui lòng nhập lại")
                }
                sv.ten = ten
                println("Chỉnh sửa tên - Chỉnh sửa tên thành công")
            }

            2 -> {
                val tuoi = readLine()?.toInt()
                sv.tuoi = tuoi
                println("Chỉnh sửa tuổi - Chỉnh sửa tuổi thành công")
            }

            3 -> return themMonHoc(sv)
            4 -> return xoaMonHoc(sv)
            5 -> return chinhSuaMonHoc(sv)
            else -> {
                println("Thoát chỉnh sửa - Thoát chỉnh sửa sinh viên")
                return KetQua.KhongXacDinh
            }
        }
    }
}