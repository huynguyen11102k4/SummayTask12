package student

abstract class Nguoi {
    abstract var ma: Int
    abstract var ten: String
    abstract var tuoi: Int?
    constructor(ma: Int, ten: String, tuoi: Int?) {
        this.ma = ma
        this.ten = ten
        this.tuoi = tuoi
    }
    open fun xuatThongTin() {
        println("MSSV: $ma")
        println("Họ tên: $ten")
        println("Tuổi: $tuoi")
    }
}