package asd.technischleerdoel.spotitube.service

import asd.technischleerdoel.spotitube.model.Track
import asd.technischleerdoel.spotitube.model.TrackResponse
import asd.technischleerdoel.spotitube.repository.TrackRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.NoSuchElementException

class TrackServiceTest {
  @Mock
  private lateinit var trackRepository: TrackRepository
  private lateinit var trackService: TrackService
  private lateinit var trackResponse: TrackResponse
  private lateinit var track: Track
  private lateinit var tracks: List<Track>

  private companion object {
   const val PLAYLISTID = "1"
  }

  @BeforeEach
  fun setup() {
   // Arrange
   MockitoAnnotations.openMocks(this)
   trackService = TrackService(trackRepository)
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
    fun `TEST - getTracks should return expected TrackResponse`() {
        // Arrange
        whenever(trackRepository.getTracks(any())).thenReturn(trackResponse)

        // Act
        val result = trackService.getTracks(PLAYLISTID)

        // Assert
        kotlin.test.assertEquals(trackResponse, result)
        verify(trackRepository).getTracks(PLAYLISTID)
    }

    @Test
    fun `getTracks should throw NoSuchElementException when playlist not found`() {
        // Arrange
        whenever(trackRepository.getTracks(any())).thenThrow(NoSuchElementException::class.java)

        // Act & Assert
        org.junit.jupiter.api.assertThrows<NoSuchElementException> {
            trackService.getTracks(PLAYLISTID)
        }

        verify(trackRepository).getTracks(PLAYLISTID)
    }
 }



