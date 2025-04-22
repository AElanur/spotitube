package asd.technischleerdoel.spotitube.businessLayer

import asd.technischleerdoel.spotitube.persistence.repository.UserRepository
import asd.technischleerdoel.spotitube.model.Token
import org.h2.security.auth.AuthenticationException
import org.springframework.stereotype.Service

@Service
class LoginService(private val userRepository: UserRepository) {
    fun verifyUser(username: String, password: String) : Token {
        val token = userRepository.findByUsername(username, password) ?: throw AuthenticationException("User does not exist")
        return Token(token = token.token, user = token.user)
    }
}