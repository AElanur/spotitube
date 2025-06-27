package asd.technischleerdoel.spotitube.service

import asd.technischleerdoel.spotitube.repository.UserRepository
import asd.technischleerdoel.spotitube.model.Token
import asd.technischleerdoel.spotitube.model.User
import org.springframework.stereotype.Service

@Service
class LoginService(private val userRepository: UserRepository) {
    fun verifyUser(user: User) : Token {
        val token = userRepository.findByUsername(user.user, user.password)
            ?: throw SecurityException()

        return Token(token = token.token, user = token.user)
    }
}