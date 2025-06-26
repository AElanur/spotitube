package asd.technischleerdoel.spotitube.service

import asd.technischleerdoel.spotitube.model.Playlist
import asd.technischleerdoel.spotitube.model.PlaylistResponse
import asd.technischleerdoel.spotitube.repository.PlaylistRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

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
        whenever(playlistRepository.deletePlaylist(any(), any())).thenReturn(1)
        whenever(playlistRepository.getPlaylists(any())).thenReturn(playlistResponse)

        // Act
        val result = playlistService.deletePlaylist(playlist.id.toString(), token)

        // Assert
        assertEquals(playlistResponse, result)
        verify(playlistRepository).deletePlaylist(playlist.id.toString(), token)
        verify(playlistRepository).getPlaylists(token)
    }

    @Test
    fun `TEST - delete playlist throws NoSuchElementException when not found`() {
        // Arrange
        whenever(playlistRepository.deletePlaylist(any(), any())).thenReturn(0)

        // Act & Assert
        assertThrows<NoSuchElementException> {
            playlistService.deletePlaylist(playlist.id.toString(), token)
        }
        verify(playlistRepository).deletePlaylist(playlist.id.toString(), token)
    }

    @Test
    fun `TEST - Create a new playlist`() {
        whenever(playlistRepository.addPlaylist(any(), any())).thenReturn(1)
        whenever(playlistRepository.getPlaylists(any())).thenReturn(playlistResponse)

        // Act
        val result = playlistService.addPlaylist(playlist, token)

        // Assert
        assertEquals(playlistResponse, result)
        verify(playlistRepository).addPlaylist(playlist, token)
        verify(playlistRepository).getPlaylists(token)
    }

    @Test
    fun `TEST - addPlaylist throws NoSuchElementException when not added`() {
        whenever(playlistRepository.addPlaylist(any(), any())).thenReturn(0)

        assertThrows<NoSuchElementException> {
            playlistService.addPlaylist(playlist, token)
        }
        verify(playlistRepository).addPlaylist(playlist, token)
    }

    @Test
    fun `TEST - Edit an existing playlist`() {
        whenever(playlistRepository.editPlaylist(any(), any())).thenReturn(1)
        whenever(playlistRepository.getPlaylists(any())).thenReturn(playlistResponse)

        // Act
        playlist.name = "New name"
        val result = playlistService.editPlaylist(playlist, token)

        // Assert
        assertEquals(playlistResponse, result)
        verify(playlistRepository).editPlaylist(playlist, token)
        verify(playlistRepository).getPlaylists(token)
    }

    @Test
    fun `TEST - editPlaylist throws NoSuchElementException when not edited`() {
        whenever(playlistRepository.editPlaylist(any(), any())).thenReturn(0)

        assertThrows<NoSuchElementException> {
            playlistService.editPlaylist(playlist, token)
        }
        verify(playlistRepository).editPlaylist(playlist, token)
    }
}