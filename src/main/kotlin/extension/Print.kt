package extension

import utils.Outcome

fun Outcome.print() {
    when (this) {
        is Outcome.Success -> println("KetQua: $value")
        is Outcome.Failure -> println("KetQua: $message")
        Outcome.Unknown -> println("KetQua: Kết quả không xác định")
    }
}