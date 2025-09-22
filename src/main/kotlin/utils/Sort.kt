package utils

sealed class Sort {
    data class ById(val ascending: Boolean) : Sort()
    data class ByName(val ascending: Boolean) : Sort()
    data class ByScore(val ascending: Boolean) : Sort()
    object Unspecified : Sort()
}
