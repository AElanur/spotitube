package asd.technischleerdoel.spotitube.persistence.repository

import asd.technischleerdoel.spotitube.persistence.connection.ConnectionManager
import asd.technischleerdoel.spotitube.model.Track
import asd.technischleerdoel.spotitube.model.TrackResponse
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException

@Repository
class TracksInPlaylistRepository(private val connectionManager: ConnectionManager) {
    fun getTracksFromPlaylist(playlistId: String): TrackResponse {
        val sql = """
        SELECT t.*, tip.offlineAvailable
        FROM spotitube.track t
        INNER JOIN spotitube.track_in_playlist tip ON t.id = tip.track_id
        WHERE tip.playlist_id = ?
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
        } catch (e: EmptyResultDataAccessException) {
            println("Error fetching tracks for playlist: ${e.message}")
            return TrackResponse(emptyList())
        }
    }

    @Transactional
    fun addTrackToPlaylist(playlistId: String, track: Track) {
        val sql = """
        INSERT INTO spotitube.track_in_playlist (track_id, playlist_id, offlineAvailable)
        VALUES (?, ?, ?)
        """
        try {
            connectionManager.jdbcTemplate.update(sql, track.id, playlistId, track.offlineAvailable)
        } catch (e: SQLException) {
            println("Error adding tracks to playlist: ${e.message}")
            throw e
        }
    }

    @Transactional
    fun deleteTrackFromPlaylist(playlistId: String, trackId: String) {
        val sql = """
        DELETE FROM spotitube.track_in_playlist
        WHERE track_id = ? AND playlist_id = ?
        """
        try {
            connectionManager.jdbcTemplate.update(sql, trackId, playlistId)
        } catch (e: SQLException) {
            println("Error deleting tracks from playlist: ${e.message}")
            throw e
        }
    }
}