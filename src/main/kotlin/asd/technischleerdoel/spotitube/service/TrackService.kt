package asd.technischleerdoel.spotitube.service

import asd.technischleerdoel.spotitube.repository.TrackRepository
import asd.technischleerdoel.spotitube.model.TrackResponse
import org.springframework.stereotype.Service

@Service
class TrackService(private val trackRepository: TrackRepository) {
    fun getTracks(playlistId: String): TrackResponse {
        return trackRepository.getTracks(playlistId)
    }
}