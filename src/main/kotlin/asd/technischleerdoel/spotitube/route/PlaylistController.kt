package asd.technischleerdoel.spotitube.route

import asd.technischleerdoel.spotitube.model.Playlist
import asd.technischleerdoel.spotitube.model.PlaylistResponse
import asd.technischleerdoel.spotitube.service.PlaylistService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/playlists")
class PlaylistController(private val playlistService: PlaylistService) {

    @GetMapping
    fun getPlaylists(@RequestParam token:String) : PlaylistResponse {
        return playlistService.getPlaylists(token)
    }

    @DeleteMapping("/{playlistId}")
    fun deletePlaylist(@PathVariable playlistId:String, @RequestParam token:String) : PlaylistResponse {
        return playlistService.deletePlaylist(playlistId, token)
    }

    @PostMapping
    fun addPlaylist(@RequestBody playlist: Playlist, @RequestParam token:String) : PlaylistResponse {
        return playlistService.addPlaylist(playlist, token)
    }

    @PutMapping("/{playlist}")
    fun editPlaylist(@RequestBody playlist: Playlist, @RequestParam token:String) : PlaylistResponse {
        return playlistService.editPlaylist(playlist, token)
    }
}