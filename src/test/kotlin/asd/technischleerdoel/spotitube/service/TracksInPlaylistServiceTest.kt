package asd.technischleerdoel.spotitube.service

import asd.technischleerdoel.spotitube.model.Track
import asd.technischleerdoel.spotitube.model.TrackResponse
import asd.technischleerdoel.spotitube.repository.TracksInPlaylistRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class TracksInPlaylistServiceTest {
    @Autowired
    lateinit var mockMvc: MockMvc

  @Mock
  private lateinit var tracksInPlaylistRepository: TracksInPlaylistRepository
  private lateinit var tracksInPlaylistService: TracksInPlaylistService
  private lateinit var trackResponse: TrackResponse
  private lateinit var track: Track
  private lateinit var tracks: List<Track>

    private companion object {
        const val PLAYLISTID = "1"
        const val TRACKID = "1"
    }

    @BeforeEach
    fun setup() {
        // Arrange
        MockitoAnnotations.openMocks(this)
        tracksInPlaylistService = TracksInPlaylistService(tracksInPlaylistRepository)
        track = Track(
            1,
            "TestName",
            "Test",
            300,
            null,
            null,
            null,
            null,
            true
        )
        tracks = listOf(track)
        trackResponse = TrackResponse(tracks)
    }

    @Test
    fun `TEST - get tracks from existing playlist`() {
        // Arrange
        whenever(tracksInPlaylistRepository.getTracksFromPlaylist(any())).thenReturn(trackResponse)

        // Act
        val result = tracksInPlaylistService.getTracksOnPlaylist(PLAYLISTID)

        // Assert
        kotlin.test.assertEquals(trackResponse, result)
        verify(tracksInPlaylistRepository).getTracksFromPlaylist(PLAYLISTID)
    }

    @Test
    fun `TEST - get tracks from existing playlist should throw NoSuchElementException`() {
        // Arrange
        whenever(tracksInPlaylistRepository.getTracksFromPlaylist(any())).thenThrow(NoSuchElementException::class.java)

        // Act & Assert
        org.junit.jupiter.api.assertThrows<NoSuchElementException> {
            tracksInPlaylistService.getTracksOnPlaylist(PLAYLISTID)
        }

        verify(tracksInPlaylistRepository).getTracksFromPlaylist(PLAYLISTID)
    }

    @Test
    fun `TEST - add tracks to existing playlist`() {
        // Arrange
        whenever(tracksInPlaylistRepository.addTrackToPlaylist(any(), any())).thenReturn(1)
        whenever(tracksInPlaylistRepository.getTracksFromPlaylist(any())).thenReturn(trackResponse)

        // Act
        val result = tracksInPlaylistService.addTrackToPlaylist(PLAYLISTID, track)

        // Assert
        kotlin.test.assertEquals(trackResponse, result)
        verify(tracksInPlaylistRepository).addTrackToPlaylist(PLAYLISTID, track)
    }

    @Test
    fun `TEST - adding tracks to existing playlist should throw NoSuchElementException`() {
        // Arrange
        whenever(tracksInPlaylistRepository.addTrackToPlaylist(any(), any())).thenThrow(NoSuchElementException::class.java)

        // Act & Assert
        org.junit.jupiter.api.assertThrows<NoSuchElementException> {
            tracksInPlaylistService.addTrackToPlaylist(PLAYLISTID, track)
        }

        verify(tracksInPlaylistRepository).addTrackToPlaylist(PLAYLISTID, track)
    }

    @Test
    fun `TEST - delete track from existing playlist`() {
        // Arrange
        whenever(tracksInPlaylistRepository.deleteTrackFromPlaylist(any(), any())).thenReturn(1)
        whenever(tracksInPlaylistRepository.getTracksFromPlaylist(any())).thenReturn(trackResponse)

        // Act
        val result = tracksInPlaylistService.deleteTrackFromPlaylist(PLAYLISTID, TRACKID)

        // Assert
        kotlin.test.assertEquals(trackResponse, result)
        verify(tracksInPlaylistRepository).deleteTrackFromPlaylist(PLAYLISTID, TRACKID)
    }

    @Test
    fun `TEST - deleting a track from existing playlist should throw NoSuchElementException`() {
        // Arrange
        whenever(tracksInPlaylistRepository.deleteTrackFromPlaylist(any(), any())).thenThrow(NoSuchElementException::class.java)

        // Act & Assert
        org.junit.jupiter.api.assertThrows<NoSuchElementException> {
            tracksInPlaylistService.deleteTrackFromPlaylist(PLAYLISTID, TRACKID)
        }

        verify(tracksInPlaylistRepository).deleteTrackFromPlaylist(PLAYLISTID, TRACKID)
    }
 }