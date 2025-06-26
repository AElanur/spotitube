package asd.technischleerdoel.spotitube.service

import asd.technischleerdoel.spotitube.model.Token
import asd.technischleerdoel.spotitube.model.User
import asd.technischleerdoel.spotitube.repository.UserRepository
import org.h2.security.auth.AuthenticationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.mockito.kotlin.any
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class LoginServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var loginService: LoginService

    private var username = "testUser"
    private val password = "testPassword"
    private val fullname = "test_user"
    private val tokenValue = "test token"
    private lateinit var user: User
    private lateinit var token: Token

    @BeforeEach
    fun setup() {
        // Arrange
        loginService = LoginService(userRepository)
        user = User(username, password, fullname)
        token = Token(tokenValue, fullname)
    }

    @Test
    fun `verify successful response on correct login`() {
        // Arrange
        whenever(userRepository.findByUsername(any(), any())).thenReturn(token)

        // Act
        val result = loginService.verifyUser(username, password)

        // Assert
        assertEquals(user.fName, result.user)
        assertTrue(result.token.isNotEmpty())
        verify(userRepository).findByUsername(any(), any())
    }

    @Test
    fun `verify exception on faulty login`() {
        // Arrange
        whenever(userRepository.findByUsername(any(), any())).thenReturn(null)
        val faultyUsername = "User test"

        // Act & Assert
        assertThrows<SecurityException> {
            loginService.verifyUser(faultyUsername, password)
        }

        verify(userRepository).findByUsername(faultyUsername, password)
    }
}