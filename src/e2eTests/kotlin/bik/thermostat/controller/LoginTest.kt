package bik.thermostat.controller

import http.Petition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginTest {
    val URL = "http://localhost:8080/user"
    val URL_LOGIN = "http://localhost:8080/login"
    val URL_LOGOUT = "http://localhost:8080/login"
    val APPLICATION_JSON = "application/json"
    val data = "{\"username\":\"user\", \"password\":\"pass\"}"
    val deleteData = "{\"username\":\"user\"}"

    @BeforeEach
    fun setup() {
        Petition.to(URL)
            .withContentType(APPLICATION_JSON)
            .sendADelete(deleteData)
        Petition.to(URL)
            .withContentType(APPLICATION_JSON)
            .sendAPost(data)
    }

    @Test
    fun should_login_user() {
        //https://blog.softtek.com/es/autenticando-apis-con-spring-y-jwt
        val response = Petition.to(URL_LOGIN)
            .withContentType(APPLICATION_JSON)
            .sendAPost(data)

        response.assertThatResponseIsOk()
            .assertThatHasHeaderThatContains("Authorization", "Bearer")
    }

    @Test
    fun should_logout_user() {
        //https://blog.softtek.com/es/autenticando-apis-con-spring-y-jwt
        val response = Petition.to(URL_LOGIN)
            .withContentType(APPLICATION_JSON)
            .sendAPost(data)
        Petition.to(URL_LOGOUT)
            .withContentType(APPLICATION_JSON)
            .withHeader("Authorization", response.headers("Authorization")!!)
            .sendAPost(data)
            .assertThatResponseIsOk()
    }

    @Test
    fun should_response_with_a_403_if_has_not_bearer() {
        //https://blog.softtek.com/es/autenticando-apis-con-spring-y-jwt
        //Petition.sendAGetTo(URL)
        //    .assertThatResponseCodeIs(401)
    }
}
