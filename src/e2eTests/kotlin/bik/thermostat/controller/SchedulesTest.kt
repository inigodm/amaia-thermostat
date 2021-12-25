package bik.thermostat.controller

import bik.thermostat.controller.LoginTest.Companion.makeLoggedPetitionTo
import bik.thermostat.schedules.model.Schedule
import bik.thermostat.utils.DBUtil
import db.DBSetup
import org.junit.jupiter.api.*

class SchedulesTest {
    val data = "{\"weekdays\":\"L,M,X\", \"startAt\":\"20:00\", \"endAt\":\"23:00\", \"desiredTemp\":18, \"active\":1}"
    val data2 = "{\"weekdays\":\"J,V\", \"startAt\":\"20:00\", \"endAt\":\"23:00\", \"desiredTemp\":18, \"active\":1}"
    val SCHEDULE_URL = "schedules"

    @BeforeEach
    fun setup() {
        DBSetup(DBUtil.BD_CONNECTION_URL).given("delete from schedules where 1 = 1")
    }

    @Test
    fun `should create a new schedule`() {
        makeLoggedPetitionTo(SCHEDULE_URL)
            .sendAPost(data)
            .assertThatResponseIsOk()
            .assertThatBodyContains("id")

        DBSetup(DBUtil.BD_CONNECTION_URL)
            .`when`("select * from schedules where weekdays = 'L,M,X'")
            .assertThatNumberOfResponses(1)
            .assertThatExistAEntryWithFields(mapOf("startAt" to "20:00",
                                                    "endAt" to "23:00",
                                                    "desiredTemp" to "18",
                                                    "active" to "1"))
    }

    @Test
    fun `should get all the schedules`() {
        makeLoggedPetitionTo(SCHEDULE_URL)
            .sendAPost(data)
            .assertThatResponseIsOk()
            .assertThatBodyContains("id")
        makeLoggedPetitionTo(SCHEDULE_URL)
            .sendAPost(data2)
            .assertThatResponseIsOk()
            .assertThatBodyContains("id")

        val response = makeLoggedPetitionTo(SCHEDULE_URL)
            .sendAGet()

        response.assertThatResponseIsOk()
            .assertThatBodyContains(data.substring(1, data.length-1))
            .assertThatBodyContains(data2.substring(1, data2.length-1))
    }

    @Test
    fun `should get selected the schedules`() {
        val aux = makeLoggedPetitionTo(SCHEDULE_URL)
            .sendAPost(data)

        val response = makeLoggedPetitionTo(SCHEDULE_URL)
            .sendAGet(mapOf("id" to aux.parseBody<Schedule>().id as String))

        response.assertThatResponseIsOk()
            .assertThatBodyContains(data.substring(1, data.length-1))
            .assertThatBodyContains(data2.substring(1, data2.length-1))
    }
}

//"insert into schedules (WEEKDAYS, STARTHOUR, ENDHOUR, DESIREDTEMP, ACTIVE) values (?,?,?,?,?)
