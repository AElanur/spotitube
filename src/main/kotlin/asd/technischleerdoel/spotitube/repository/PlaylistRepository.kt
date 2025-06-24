package asd.technischleerdoel.spotitube.repository

import asd.technischleerdoel.spotitube.repository.connection.ConnectionManager
import asd.technischleerdoel.spotitube.model.Playlist
import asd.technischleerdoel.spotitube.model.PlaylistResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class PlaylistRepository @Autowired constructor(private val connectionManager: ConnectionManager) {
    fun getPlaylists(token: String): PlaylistResponse {
        val sql = """
            SELECT p.id, p.name, 
            IF(u.token = ?, TRUE, FALSE) as is_owner
            FROM spotitube.playlist p
            JOIN spotitube.user_token u ON p.owner = u.user_id
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
        } catch (e: DataAccessException) {
            println("No playlists found: ${e.message}")
            throw e
        }
    }

    @Transactional
    fun deletePlaylist(id: String, token: String): Int {
        val sql = """
                DELETE p 
                FROM spotitube.playlist p 
                INNER JOIN spotitube.user_token u ON p.owner = u.user_id 
                WHERE u.token = ? AND p.id = ?
            """
        return try {
            connectionManager.jdbcTemplate.update(sql, token, id)
        } catch (e: DataAccessException) {
            println("Error deleting playlist: ${e.message}")
            throw NoSuchElementException("Failed to delete playlist", e)
        }
    }

    @Transactional
    fun addPlaylist(playlist: Playlist, token: String): Int {
        val sql = """
                INSERT INTO spotitube.playlist (name, owner)
                SELECT ?, u.user_id
                FROM spotitube.user_token u
                WHERE u.token = ?
            """
        return try {
            connectionManager.jdbcTemplate.update(sql, playlist.name, token)
        } catch (e: DataAccessException) {
            println("Error inserting playlist, 0 records changed: $e")
            throw e
        }
    }

    @Transactional
    fun editPlaylist(playlist: Playlist, token: String): Int {
        val sql = """
                UPDATE spotitube.playlist p
                SET p.name = ? 
                WHERE p.id = ?
            """
        return try {
            connectionManager.jdbcTemplate.update(sql, playlist.name, playlist.id)
        } catch (e: DataAccessException) {
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
        } catch (e: DataAccessException) {
            println("Unexpected error occurred: ${e.message}")
            throw e
        }
    }
}