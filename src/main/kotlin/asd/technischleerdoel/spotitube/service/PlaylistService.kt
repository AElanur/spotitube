package asd.technischleerdoel.spotitube.service

import asd.technischleerdoel.spotitube.repository.PlaylistRepository
import asd.technischleerdoel.spotitube.model.Playlist
import asd.technischleerdoel.spotitube.model.PlaylistResponse
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

@Service
class PlaylistService(private val playlistRepository: PlaylistRepository) {
    fun getPlaylists(token: String): PlaylistResponse {
        return playlistRepository.getPlaylists(token)
    }

    fun deletePlaylist(id: String, token: String) : PlaylistResponse {
        return if (playlistRepository.deletePlaylist(id, token) > 0) {
            getPlaylists(token)
        } else {
            throw NoSuchElementException("Failed to delete playlist")
        }
    }

    fun addPlaylist(playlist: Playlist, token: String) : PlaylistResponse {
        return if (playlistRepository.addPlaylist(playlist, token) > 0) {
            getPlaylists(token)
        } else {
            throw NoSuchElementException("Failed to add playlist")
        }
    }

    fun editPlaylist(playlist: Playlist, token: String) : PlaylistResponse {
        return if (playlistRepository.editPlaylist(playlist, token) > 0) {
            getPlaylists(token)
        } else {
            throw NoSuchElementException("Failed to edit playlist")
        }
    }
}