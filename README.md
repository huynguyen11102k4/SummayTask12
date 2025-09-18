# Student Management System

Ứng dụng console bằng Kotlin để quản lý sinh viên: thêm, sửa, xóa, tìm kiếm, in danh sách, và top 10 theo điểm TB.

## Tính năng
- Thêm / Xóa / Sửa sinh viên
- Thêm / Xóa / Sửa môn học
- Tìm kiếm sinh viên theo mã
- In danh sách sinh viên
- In top 10 sinh viên điểm cao nhất

## Cấu trúc
Dự án được xây dựng với các lớp chính sau:
- SinhVien: Đại diện cho một sinh viên
- MonHoc: Lưu trữ thông tin môn học và điểm số
- StudentRepo: Quản lý danh sách sinh viên
- Qlsv.kt: Chứa logic chính và menu tương tác
- Các enum và sealed class (LuaChon, ChinhSuaSv, KetQua) để quản lý các tùy chọn và kết quả
