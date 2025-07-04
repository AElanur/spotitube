package asd.technischleerdoel.spotitube.repository

import asd.technischleerdoel.spotitube.model.Token
import asd.technischleerdoel.spotitube.repository.connection.ConnectionManager
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException

@Repository
class UserTokenRepository(private val connectionManager: ConnectionManager) {
    @Transactional
    fun fetchToken(userId: String): Token? {
        val sql = "SELECT ut.token, u.fullname " +
                "FROM spotitube.user_token ut " +
                "LEFT JOIN spotitube.user u " +
                "ON ut.user_id = u.id " +
                "WHERE user_id = ?"
        return try {
            connectionManager.jdbcTemplate.queryForObject(sql, { rs, _ ->
                rs.getString("token")?.let {
                    Token(
                        user = rs.getString("fullname"),
                        token = rs.getString("token")
                    )
                } ?: createTokenForUser(userId)
            }, userId)
        } catch (e: EmptyResultDataAccessException) {
            println("Error fetching token: ${e.message}")
            null
        }
    }

    @Transactional
    fun createTokenForUser(userId: String): Token? {
        val sql = "INSERT INTO spotitube.user_token (token, user_id)  VALUES (?, ?)"
        return try {
            connectionManager.jdbcTemplate.update(sql, generateToken(), userId)
            fetchToken(userId)
        } catch (e: SQLException) {
            println("Error adding token to DB: ${e.message}")
            throw e
        }
    }

    fun generateToken(length: Int = 12): String {
        val allowedChars = ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .chunked(4)
            .joinToString("-") { it.joinToString("") }
    }
}