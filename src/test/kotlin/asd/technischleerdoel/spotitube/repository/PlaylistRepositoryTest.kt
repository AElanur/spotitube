package asd.technischleerdoel.spotitube.repository
import asd.technischleerdoel.spotitube.repository.connection.ConnectionManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet

class PlaylistRepositoryTest {
    private val mockJdbcTemplate: JdbcTemplate = mockk()
    private val connectionManager: ConnectionManager = mockk {
        every { jdbcTemplate } returns mockJdbcTemplate
    }
    private val repository = PlaylistRepository(connectionManager)
}