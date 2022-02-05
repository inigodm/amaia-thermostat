package bik.thermostat.controller

import bik.thermostat.utils.DBUtil
import bik.thermostat.utils.HttpUtil
import db.DBSetup
import http.Petition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginTest {
    val URL = "http://localhost:8080/user"
    val URL_LOGOUT = "http://localhost:8080/login"
    val deleteData = "{\"username\":\"user\"}"
    val URL_SCHEDULE = "schedules"

    companion object {
        val URL_LOGIN = "http://localhost:8080/login"
        val data = "{\"username\":\"user\", \"password\":\"pass\"}"

        @JvmStatic fun makeLoggedPetitionTo(url: String) : Petition{
            val response = Petition.to(URL_LOGIN)
                .withContentType(HttpUtil.APPLICATION_JSON)
                .sendAPost(this.data)
            return Petition.to(url)
                .withContentType(HttpUtil.APPLICATION_JSON)
                .withHeader("Authorization", response.headers("Authorization")!!)
        }
    }

    @BeforeEach
    fun setup() {
        DBSetup(DBUtil.BD_CONNECTION_URL).givenEmptyTable("users")
        Petition.to(URL)
            .withContentType(HttpUtil.APPLICATION_JSON)
            .sendAPost(data)
    }

    @Test
    fun `should login user`() {
        //https://blog.softtek.com/es/autenticando-apis-con-spring-y-jwt
        val response = Petition.to(URL_LOGIN)
            .withContentType(HttpUtil.APPLICATION_JSON)
            .sendAPost(data)

        response.assertThatResponseIsOk()
            .assertThatHasHeaderThatContains("Authorization", "Bearer")
    }

    @Test
    fun should_logout_user() {
        //https://blog.softtek.com/es/autenticando-apis-con-spring-y-jwt
        val response = Petition.to(URL_LOGIN)
            .withContentType(HttpUtil.APPLICATION_JSON)
            .sendAPost(data)

        Petition.to(URL_LOGOUT)
            .withContentType(HttpUtil.APPLICATION_JSON)
            .withHeader("Authorization", response.headers("Authorization")!!)
            .sendAGet()
            .assertThatResponseIsOk()

        Petition.to(URL_SCHEDULE)
            .withContentType(HttpUtil.APPLICATION_JSON)
            .withHeader("Authorization", response.headers("Authorization")!!)
            .sendAGet()
            .assertThatResponseCodeIs(401)
    }

    @Test
    fun should_response_with_a_401_if_not_logged() {
        //https://blog.softtek.com/es/autenticando-apis-con-spring-y-jwt
        Petition.to(URL_SCHEDULE)
            .withContentType(HttpUtil.APPLICATION_JSON)
            .sendAGet()
            .assertThatResponseCodeIs(401)
    }
}
