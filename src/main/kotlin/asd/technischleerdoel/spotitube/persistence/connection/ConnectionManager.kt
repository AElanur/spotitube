package asd.technischleerdoel.spotitube.persistence.connection

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class ConnectionManager @Autowired constructor(val jdbcTemplate: JdbcTemplate) {
    fun executeQuery(sql: String): List<Map<String, Any>> {
        return jdbcTemplate.queryForList(sql)
    }

    fun executeUpdate(sql: String): Int {
        return jdbcTemplate.update(sql)
    }

    fun getConnection() = jdbcTemplate.dataSource?.connection
}
