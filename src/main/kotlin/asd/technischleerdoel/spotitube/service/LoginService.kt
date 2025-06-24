package asd.technischleerdoel.spotitube.service

import asd.technischleerdoel.spotitube.repository.UserRepository
import asd.technischleerdoel.spotitube.model.Token
import org.springframework.stereotype.Service

@Service
class LoginService(private val userRepository: UserRepository) {
    fun verifyUser(username: String, password: String) : Token {
        val token = userRepository.findByUsername(username, password)
            ?: throw SecurityException()

        return Token(token = token.token, user = token.user)
    }
}