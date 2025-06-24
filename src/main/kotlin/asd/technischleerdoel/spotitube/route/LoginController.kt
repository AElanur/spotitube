package asd.technischleerdoel.spotitube.route

import asd.technischleerdoel.spotitube.model.Token
import asd.technischleerdoel.spotitube.model.User
import asd.technischleerdoel.spotitube.service.LoginService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(private val loginService: LoginService) {
    @PostMapping("/login")
    fun login(@RequestBody user: User) : Token {
        return loginService.verifyUser(user.user, user.password)
    }
}