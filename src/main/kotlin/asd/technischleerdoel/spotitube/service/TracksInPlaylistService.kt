package asd.technischleerdoel.spotitube.service

import asd.technischleerdoel.spotitube.model.Track
import asd.technischleerdoel.spotitube.repository.TracksInPlaylistRepository
import asd.technischleerdoel.spotitube.model.TrackResponse
import org.springframework.stereotype.Service

@Service
class TracksInPlaylistService(private val tracksInPlaylistRepository: TracksInPlaylistRepository) {
    fun getTracksOnPlaylist(playlistId: String): TrackResponse {
        return tracksInPlaylistRepository.getTracksFromPlaylist(playlistId)
    }

    fun addTrackToPlaylist(playlistId: String, track: Track) : TrackResponse {
        tracksInPlaylistRepository.addTrackToPlaylist(playlistId, track)
        return tracksInPlaylistRepository.getTracksFromPlaylist(playlistId)
    }

    fun deleteTrackFromPlaylist(playlistId: String, trackId: String) : TrackResponse {
        tracksInPlaylistRepository.deleteTrackFromPlaylist(playlistId, trackId)
        return tracksInPlaylistRepository.getTracksFromPlaylist(playlistId)
    }
}