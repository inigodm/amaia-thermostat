package bik.thermostat.sample.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import kotlin.Throws
import bik.thermostat.exceptions.ThermostatException
import bik.thermostat.user.infraestructure.UserManager

@RestController
class SampleController(var userManager: UserManager) {
    @GetMapping("helloworld")
    @Throws(ThermostatException::class)
    fun helloWorld(): String {
        userManager.createTable()
        return "Hello world!"
    }
}
