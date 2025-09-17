package com.example.summaytask12

import android.util.Log
import com.example.summaytask12.data.ChinhSuaSv
import com.example.summaytask12.data.KetQua
import com.example.summaytask12.data.LuaChon
import com.example.summaytask12.data.MonHoc
import com.example.summaytask12.data.StudentRepo
import com.example.summaytask12.data.inRa
import com.example.summaytask12.student.SinhVien

fun quanLySinhVien() {
    Log.d("Quản lý sinh viên", "Summary Task 12")
    for (lc in LuaChon.entries) {
        Log.d("Lựa chọn", "${lc.giaTri}. ${lc.name.replace('_', ' ')}")
    }
    while (true) {
        Log.d("Nhập lựa chọn", "Nhập lựa chọn của bạn (1-${LuaChon.entries.size}), nhập khác để thoát:")
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
                Log.d("Thoát chương trình", "Cảm ơn bạn đã sử dụng chương trình!")
                break
            }
        }
        kq.inRa()
    }
}

fun readInt(p: String): Int {
    while (true) {
        Log.d("Input", p)
        val input = readLine()
        try {
            if (input != null && input.toIntOrNull() != null) {
                return input.toInt()
            }
        } catch (e: NumberFormatException) {
            Log.e("Lỗi", "Vui lòng nhập số nguyên hợp lệ")
        }
    }
}

fun List<SinhVien>.top10(): List<SinhVien> {
    return this.sortedByDescending { it.tinhDiemTB() }.take(10)
}

fun themSV(): KetQua {
    Log.d("Thêm sinh viên", "Nhập thông tin sinh viên:")
    Log.d("Bước 1", "Nhập mã sinh viên (hoặc để trống để tự động tạo mã):")
    val maSV = readLine()?.toInt() ?: SinhVien.getAutoId()
    Log.d("Bước 2", "Nhập tên sinh viên:")
    val ten = readLine() ?: Log.e("Lỗi", "Tên không được để trống").let { return KetQua.ThatBai("Vui lòng nhập lại") }
    Log.d("Bước 3", "Nhập tuổi sinh viên:")
    val tuoi = readLine()?.toInt()
    Log.d("Bước 4", "Nhập số môn học:")
    val soMonHoc = readLine()?.toInt() ?: 0
    val dsMonHoc = mutableSetOf<MonHoc>()
    repeat(soMonHoc) {
        Log.d("Nhập thông tin môn học thứ ${it + 1}", "")
        Log.d("Mã môn học", "")
        val maMonHoc = readLine()
        Log.d("Tên môn học", "")
        val tenMonHoc = readLine()
        Log.d("Điểm môn học", "")
        val diemMonHoc = readLine()!!.toDouble()
        Log.d("Số tín chỉ", "")
        val soTinChi = readLine()!!.toInt()
        val mh = MonHoc(maMonHoc, tenMonHoc.toString(), diemMonHoc, soTinChi)
        dsMonHoc.add(mh)
    }
    val sv = SinhVien(
        ma = maSV,
        ten = ten,
        tuoi = tuoi,
        dsMonHoc = dsMonHoc
    )
    StudentRepo.themSV(sv)
    return KetQua.ThanhCong("Thêm sinh viên thành công")
}

fun inThongTinSV(): KetQua {
    StudentRepo.inDS()
    return KetQua.ThanhCong("In danh sách sinh viên thành công")
}

fun timKiemSV(): KetQua {
    val ma = readInt("Nhập mã sinh viên cần tìm:")
    val sv = StudentRepo.timSV(ma)?.also {
        it.xuatThongTin()
        return KetQua.ThanhCong("Tìm sinh viên thành công sinh viên có mã $ma")
    } ?:  return KetQua.ThatBai("Không tìm thấy sinh viên có mã $ma")
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

fun chinhSuaSV(ma: Int) : KetQua{
    val sv = StudentRepo.timSV(ma)
    if(sv == null){
        return KetQua.ThatBai("Không tìm thấy sinh viên có mã $ma")
    }
    Log.d("Chỉnh sửa sinh viên", "Lua chọn thông tin cần chỉnh sửa:")
    for(cs in ChinhSuaSv.entries){
        Log.d("Chỉnh sửa sinh viên", "${cs.giaTri}. ${cs.name.replace('_', ' ')}")
    }
}
