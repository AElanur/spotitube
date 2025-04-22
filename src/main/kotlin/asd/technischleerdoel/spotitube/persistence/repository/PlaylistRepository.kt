package asd.technischleerdoel.spotitube.persistence.repository

import asd.technischleerdoel.spotitube.persistence.connection.ConnectionManager
import asd.technischleerdoel.spotitube.model.Playlist
import asd.technischleerdoel.spotitube.model.PlaylistResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException

@Repository
class PlaylistRepository @Autowired constructor(private val connectionManager: ConnectionManager) {
    fun getPlaylists(token: String): PlaylistResponse {
        val sql = """
        SELECT p.id, p.name, 
        IF(u.token = ?, TRUE, FALSE) as is_owner
        FROM spotitube.playlist p
        INNER JOIN spotitube.user_token u ON p.owner = u.user_id
        """
        return try {
            val playlists = connectionManager.jdbcTemplate.query(sql, { rs, _ ->
                Playlist(
                    id = rs.getInt("id"),
                    name = rs.getString("name"),
                    owner = rs.getBoolean("is_owner")
                )
            }, token)
            PlaylistResponse(playlists, getTotalLengthPlaylist())
        } catch (e: SQLException) {
            println("No playlists found: ${e.message}")
            throw e
        }
    }

    @Transactional
    fun deletePlaylist(id: String, token: String): PlaylistResponse {
        val sql = """
                DELETE p 
                FROM spotitube.playlist p 
                LEFT JOIN spotitube.user_token u ON p.owner = u.id 
                WHERE u.token = ? AND p.id = ?
            """
        return try {
            connectionManager.jdbcTemplate.update(sql, token, id)
            getPlaylists(token)
        } catch (e: SQLException) {
            println("Error deleting playlist, 0 records changed: ${e.message}")
            throw e
        }
    }

    @Transactional
    fun addPlaylist(playlist: Playlist, token: String): PlaylistResponse {
        val sql = """
                INSERT INTO spotitube.playlist (name, owner)
                SELECT ?, u.id
                FROM spotitube.user_token u
                WHERE u.token = ?
            """
        return try {
            connectionManager.jdbcTemplate.update(sql, playlist.name, token)
            getPlaylists(token)
        } catch (e: SQLException) {
            println("Error inserting playlist, 0 records changed: ${e.message}")
            throw e
        }
    }

    @Transactional
    fun editPlaylist(playlist: Playlist, token: String): PlaylistResponse {
        val sql = """
                UPDATE spotitube.playlist p
                SET p.name = ? 
                WHERE p.id = ?
            """
        return try {
            val rowsAffected = connectionManager.jdbcTemplate.update(sql, playlist.name, playlist.id)
            check(rowsAffected > 0) {
                throw NoSuchElementException("No playlist found with id ${playlist.id} for the given token")
            }
            getPlaylists(token)
        } catch (e: SQLException) {
            println("Error deleting playlist, 0 records changed: ${e.message}")
            throw e
        }
    }

    private fun getTotalLengthPlaylist(): Int {
        val sql = """
        SELECT SUM(duration) as TOTAL_COUNT
        FROM spotitube.track_in_playlist tip 
        INNER JOIN spotitube.track t ON tip.track_id = t.id
        """
        return try {
            connectionManager.jdbcTemplate.queryForObject(sql, Int::class.java) ?: 0
        } catch (e: SQLException) {
            println("Unexpected error occurred: ${e.message}")
            throw e
        }
    }
}