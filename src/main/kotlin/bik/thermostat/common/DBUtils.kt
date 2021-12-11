package bik.thermostat.common

import bik.thermostat.exceptions.ThermostatError
import bik.thermostat.exceptions.ThermostatException
import java.sql.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class DBUtils {

    @Value("\${spring.datasource.url}") lateinit var BD_CONNECTION_URL: String

    @Synchronized
    @Throws(ThermostatException::class)
    fun executeUpdate(sql: String?) {
        try {
            println("connectiong to $BD_CONNECTION_URL" )
            connect(BD_CONNECTION_URL)
                .use { conn -> conn.createStatement().use { stmt -> stmt.executeUpdate(sql) } }
        } catch (e: SQLException) {
            e.printStackTrace()
            throw ThermostatException(e.message, e)
        }
    }

    @Throws(ThermostatException::class)
    fun executeQuery(sql: String?): ResultSet? {
        var rs: ResultSet? = null
        try {
            connect(BD_CONNECTION_URL)
                .use { conn -> conn.createStatement().use { stmt -> rs = stmt.executeQuery(sql) } }
        } catch (e: SQLException) {
            e.printStackTrace()
            throw ThermostatException(e.message, e)
        }
        return rs
    }

    @Synchronized
    @Throws(ThermostatException::class)
    fun executeUpdate(sql: String, function: (PreparedStatement) -> Unit) {
        try {
            connect(BD_CONNECTION_URL)
                .use { conn -> conn.prepareStatement(sql)
                    .use { stmt -> function.invoke(stmt) } }
        } catch (e: SQLException) {
            e.printStackTrace()
            throw ThermostatException(e.message, e)
        }
    }

    @Throws(ThermostatException::class)
    fun executeQuery(sql: String?, function : (PreparedStatement) -> ResultSet): ResultSet {
        var rs: ResultSet
        try {
            rs = connect(BD_CONNECTION_URL)
                .use { conn -> conn.prepareStatement(sql).use { stmt -> function.invoke(stmt) } }
        } catch (e: SQLException) {
            e.printStackTrace()
            throw ThermostatException(e.message, e)
        }
        return rs
    }

    private fun connect(url: String): Connection {
        try {
            return DriverManager.getConnection(url)
        } catch (e: SQLException) {
            throw ThermostatError("Unable to create DB connection", e)
        }
    }
}
