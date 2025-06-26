package asd.technischleerdoel.spotitube.service

import asd.technischleerdoel.spotitube.model.Track
import asd.technischleerdoel.spotitube.repository.TracksInPlaylistRepository
import asd.technischleerdoel.spotitube.model.TrackResponse
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

@Service
class TracksInPlaylistService(private val tracksInPlaylistRepository: TracksInPlaylistRepository) {
    fun getTracksOnPlaylist(playlistId: String): TrackResponse {
        return tracksInPlaylistRepository.getTracksFromPlaylist(playlistId)
    }

    fun addTrackToPlaylist(playlistId: String, track: Track) : TrackResponse {
        return if (tracksInPlaylistRepository.addTrackToPlaylist(playlistId, track) > 0) {
            tracksInPlaylistRepository.getTracksFromPlaylist(playlistId)
        } else {
            throw NoSuchElementException()
        }
    }

    fun deleteTrackFromPlaylist(playlistId: String, trackId: String): TrackResponse {
        return if (tracksInPlaylistRepository.deleteTrackFromPlaylist(playlistId, trackId) > 0) {
            tracksInPlaylistRepository.getTracksFromPlaylist(playlistId)
        } else {
            throw NoSuchElementException()
        }
    }
}