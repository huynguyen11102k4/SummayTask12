package utils

sealed class Outcome {
    data class Success(val value: String) : Outcome()
    data class Failure(val message: String) : Outcome()
    object Unknown : Outcome()
}