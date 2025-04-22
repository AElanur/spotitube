package asd.technischleerdoel.spotitube.model

data class Playlist (
    val id: Int,
    val name: String,
    val owner: Boolean,
    val tracks: List<Track> = emptyList()
)

