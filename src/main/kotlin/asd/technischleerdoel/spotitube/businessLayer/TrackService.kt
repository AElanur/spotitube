package asd.technischleerdoel.spotitube.businessLayer

import asd.technischleerdoel.spotitube.persistence.repository.TrackRepository
import asd.technischleerdoel.spotitube.model.TrackResponse
import org.springframework.stereotype.Service

@Service
class TrackService(private val trackRepository: TrackRepository) {
    fun getTracks(playlistId: String): TrackResponse {
        return trackRepository.getTracks(playlistId)
    }
}