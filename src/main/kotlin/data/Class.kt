package data

import kotlinx.serialization.Serializable

@Serializable
data class Class(
    var id: String,
    var subjectId: String,
    var teacherId: Int
)
