package asd.technischleerdoel.spotitube.model
import java.util.Date

data class Track (
    val id: Int,
    val title: String,
    val performer: String,
    val duration: Int,
    val album: String? = null,
    val playcount: Int? = null,
    val publicationDate: Date? = null,
    val description: String? = null,
    val offlineAvailable: Boolean = false
)
