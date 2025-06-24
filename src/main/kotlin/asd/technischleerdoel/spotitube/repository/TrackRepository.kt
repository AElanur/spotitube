package asd.technischleerdoel.spotitube.repository

import asd.technischleerdoel.spotitube.repository.connection.ConnectionManager
import asd.technischleerdoel.spotitube.model.Track
import asd.technischleerdoel.spotitube.model.TrackResponse
import org.springframework.stereotype.Repository

@Repository
class TrackRepository(private val connectionManager: ConnectionManager) {

    fun getTracks(playlistId: String): TrackResponse {
        val sql = """
            SELECT t.*, tip.offlineAvailable
            FROM spotitube.track t
            LEFT JOIN spotitube.track_in_playlist tip ON t.id = tip.track_id AND tip.playlist_id = ?
            WHERE tip.playlist_id IS NULL
        """
        return try {
            val tracks = connectionManager.jdbcTemplate.query(sql, { rs, _ ->
                Track(
                    id = rs.getInt("id"),
                    title = rs.getString("title"),
                    performer = rs.getString("performer"),
                    duration = rs.getInt("duration"),
                    album = rs.getString("album"),
                    playcount = rs.getInt("playcount"),
                    publicationDate = rs.getDate("publicationDate"),
                    description = rs.getString("description"),
                    offlineAvailable = rs.getInt("offlineAvailable") == 1,
                )
            }, playlistId)
            TrackResponse(tracks)
        } catch (e: Exception) {
            println("Error fetching tracks: ${e.message}")
            return TrackResponse(emptyList())
        }
    }
}