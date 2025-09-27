package data

import kotlinx.serialization.Serializable

@Serializable
data class Enrollment(
    var classId: String,
    var grade: Double = 0.0
)
