package asd.technischleerdoel.spotitube.repository

import asd.technischleerdoel.spotitube.model.Token
import asd.technischleerdoel.spotitube.repository.connection.ConnectionManager
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class UserRepository(private val connectionManager: ConnectionManager, private val userTokenRepository: UserTokenRepository) {
    @Transactional
    fun findByUsername(username: String, password: String): Token? {
        val sql = "SELECT u.id, u.fullname\n" +
                "FROM spotitube.user u\n" +
                "WHERE u.user = ? AND u.password = ?"
        return try {
            connectionManager.jdbcTemplate.queryForObject(sql, { rs, _ ->
                userTokenRepository.fetchToken(rs.getString("id"))
            }, username, password)
        } catch (e: EmptyResultDataAccessException) {
            println("Error fetching user: ${e.message}")
            null
        }
    }
}