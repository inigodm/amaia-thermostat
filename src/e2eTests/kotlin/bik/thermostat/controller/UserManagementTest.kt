package bik.thermostat.controller

import http.Petition
import db.DBSetup
import org.junit.jupiter.api.*

class UserManagementTest {
    val URL = "http://localhost:8080/user"
    val data = "{\"username\":\"user\", \"password\":\"pass\"}"
    val deleteData = "{\"username\":\"user\"}"
    val BD_CONNECTION_URL = "jdbc:sqlite:/home/inigo/code/thermostat/db/thermostat.db"
    val APPLICATION_JSON = "application/json"

    @BeforeEach
    fun setup() {
        Petition.to(URL)
            .withContentType(APPLICATION_JSON)
            .sendADelete(deleteData)
    }

    @Test
    fun should_create_a_new_user() {
        // WHEN
        val response = Petition.to(URL)
            .withContentType(APPLICATION_JSON)
            .sendAPost(data)
        //THEN
        response.assertThatResponseIsOk()
        var res = DBSetup(BD_CONNECTION_URL)
            .`when`("select * from users where username = 'user'")
            .assertThatNumberOfResponses(1)
            .assertThatExistAEntryWithFields(mapOf("USERNAME" to "user"))
    }
}

