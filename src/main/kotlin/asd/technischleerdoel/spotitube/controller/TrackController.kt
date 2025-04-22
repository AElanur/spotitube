package asd.technischleerdoel.spotitube.controller

import asd.technischleerdoel.spotitube.model.TrackResponse
import asd.technischleerdoel.spotitube.businessLayer.TrackService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tracks")
class TrackController(private val trackService: TrackService) {
    @GetMapping
    fun getTracks(@RequestParam("forPlaylist") playlistId: String) : TrackResponse {
        return trackService.getTracks(playlistId)
    }
}