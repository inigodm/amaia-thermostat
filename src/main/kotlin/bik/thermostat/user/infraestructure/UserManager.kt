package bik.thermostat.user.infraestructure

import bik.thermostat.common.DBUtils
import bik.thermostat.exceptions.ThermostatError
import bik.thermostat.exceptions.ThermostatException
import bik.thermostat.user.model.User
import org.springframework.stereotype.Component
import java.lang.Exception
import java.sql.SQLException

@Component
class UserManager(var dbUtils: DBUtils) {
        @Throws(ThermostatException::class)
        fun createTable() {
            try {
                println("Deleting table users")
                dbUtils.executeUpdate("drop table users")
            } catch (_: Exception) {
            }
            println("trying to generate table Users")
            createUserTable()
            println("Adding a user")
            createUser("inigo", "password")
            println("Done")
        }

        @Throws(ThermostatException::class)
        private fun createUserTable() {
            val sql = "CREATE TABLE USERS " +
                    "(USERNAME           TEXT    NOT NULL PRIMARY KEY, " +
                    " PASSWORD           TEXT     NOT NULL," +
                    " SALT			TEXT  	   NOT NULL)"
            dbUtils.executeUpdate(sql)
        }

        fun createUser(user: String, pass: String) {
            val salt = SHA256.generateSalt()
            val hash = SHA256.hash(pass, salt)
            val sql = "insert into users (username, password, salt) values (?, '$hash', '$salt')"
            dbUtils.executeUpdate(sql) {
                it.setString(1, user)
                it.executeUpdate()
            }
        }


    @Throws(ThermostatException::class)
    fun updatePassword(user: String, pass: String) {
        val salt = SHA256.generateSalt()
        val hash = SHA256.hash(pass, salt)
        val sql = "update users set password = '$hash', salt= '$salt' where username = ?"
        dbUtils.executeUpdate(sql) {
            it.setString(1, user)
            it.executeUpdate()
        }
    }

    fun login(username: String?, pass: String?): String {
        val user = findUser(username, pass)
        val isError = null == user
        return if (isError) {
            "login.jsp"
        } else {
            "site/index"
        }
    }

    private fun findUser(user: String?, pass: String?): User? {
        if (user == null || pass == null) {
            return null
        }
        val sql = "select username, password, salt from users where username = ?"
        try {
            dbUtils.executeQuery(sql) {
                it.setString(1, user)
                it.executeQuery()
            }.use { rs ->
                if (!rs.next()) {
                    return null
                }
                return returnValidUser(rs.getString(1), rs.getString(2), rs.getString(3), pass)
            }
        } catch (e: SQLException) {
            throw ThermostatError(e.message, e)
        }
    }

    private fun returnValidUser(name: String, hash: String, salt: String, pass: String): User? {
        if (!SHA256.isValidHash(hash, salt, pass)) {
            return null
        }
        return User(name)
    }

    fun deleteUser(userName: String) {
        dbUtils.executeUpdate("Delete from users where username = ?") {
            it.setString(1, userName)
            it.executeUpdate()
        }
    }


}
