package asd.technischleerdoel.spotitube.businessLayer

import asd.technischleerdoel.spotitube.model.Playlist
import asd.technischleerdoel.spotitube.model.PlaylistResponse
import asd.technischleerdoel.spotitube.persistence.repository.PlaylistRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class PlaylistServiceTest {

    @Mock
    private lateinit var playlistRepository: PlaylistRepository
    private lateinit var playlistService: PlaylistService
    private lateinit var playlistResponse: PlaylistResponse
    private lateinit var playlist: Playlist
    private lateinit var playlists: List<Playlist>

    private companion object {
        const val playlistLength = 300
        const val token = "test-token"
    }

    @BeforeEach
    fun setup() {
        // Arrange
        MockitoAnnotations.openMocks(this)
        playlistService = PlaylistService(playlistRepository)
        playlist = Playlist(
            1,
            "TestName",
            false
        )
        playlists = listOf(playlist)
        playlistResponse = PlaylistResponse(playlists, playlistLength)
    }

    @Test
    fun `TEST - getPlaylists should return expected PlaylistResponse`() {
        // Arrange
        whenever(playlistRepository.getPlaylists(any())).thenReturn(playlistResponse)

        // Act
        val result = playlistService.getPlaylists(token)

        // Assert
        assertEquals(playlistResponse, result)
        verify(playlistRepository).getPlaylists(token)
    }

    @Test
    fun `TEST - delete playlist`() {
        // Arrange
        whenever(playlistRepository.deletePlaylist(any(), any())).thenReturn(playlistResponse)

        // Act
        val result = playlistService.deletePlaylist(playlist.id.toString(), token)

        // Assert
        assertEquals(playlistResponse, result)
        verify(playlistRepository).deletePlaylist(playlist.id.toString(), token)
    }

    @Test
    fun addPlaylist() {
    }
}