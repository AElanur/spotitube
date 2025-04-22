package asd.technischleerdoel.spotitube.businessLayer

import asd.technischleerdoel.spotitube.persistence.repository.PlaylistRepository
import asd.technischleerdoel.spotitube.model.Playlist
import asd.technischleerdoel.spotitube.model.PlaylistResponse
import org.springframework.stereotype.Service

@Service
class PlaylistService(private val playlistRepository: PlaylistRepository) {
    fun getPlaylists(token: String): PlaylistResponse {
        return playlistRepository.getPlaylists(token)
    }

    fun deletePlaylist(id: String, token: String) : PlaylistResponse {
        return playlistRepository.deletePlaylist(id, token)
    }

    fun addPlaylist(playlist: Playlist, token: String) : PlaylistResponse {
        return playlistRepository.addPlaylist(playlist, token)
    }

    fun editPlaylist(playlist: Playlist, token: String) : PlaylistResponse {
        return playlistRepository.editPlaylist(playlist, token)
    }
}