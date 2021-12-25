package bik.thermostat.controller

import org.junit.jupiter.api.Test

class TemperatureManagementTest {
    @Test
    fun `should get room temperature`() {
        val response = LoginTest.makeLoggedPetitionTo("temperature")
            .sendAGet()
        
        response
            .assertThatResponseIsOk()
            .assertThatBodyContainsKey("roomTemperature")
    }

    @Test
    fun should_increase_room_temperature() {
        val responseInit = LoginTest.makeLoggedPetitionTo("temperature")
            .sendAGet().body()
        val response = LoginTest.makeLoggedPetitionTo("temperature")
            .sendAPut(mapOf("change" to "1"))
        
        response
            .assertThatResponseIsOk()
            .assertThatBodyContains(mapOf<String, Any?>("roomTemperature" to (responseInit["roomTemperature"] as Int - 1)))
    }
}
