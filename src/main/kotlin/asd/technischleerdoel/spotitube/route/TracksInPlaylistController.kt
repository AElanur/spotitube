package asd.technischleerdoel.spotitube.route

import asd.technischleerdoel.spotitube.model.Track
import asd.technischleerdoel.spotitube.model.TrackResponse
import asd.technischleerdoel.spotitube.service.TracksInPlaylistService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("playlists/{playlistId}/tracks")
class TracksInPlaylistController(private val tracksInPlaylistService: TracksInPlaylistService) {

    @GetMapping
    fun getTracksInPlaylists(@PathVariable playlistId: String) : TrackResponse {
        return tracksInPlaylistService.getTracksOnPlaylist(playlistId)
    }

    @PostMapping
    fun addTracksToPlaylist(@PathVariable playlistId: String, @RequestBody track: Track) : TrackResponse {
        return tracksInPlaylistService.addTrackToPlaylist(playlistId, track)
    }

    @DeleteMapping("/{trackId}")
    fun deleteTrackFromPlaylist(@PathVariable playlistId: String, @PathVariable trackId: String) : TrackResponse {
        return tracksInPlaylistService.deleteTrackFromPlaylist(playlistId, trackId)
    }
}