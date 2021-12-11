package bik.thermostat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ThermostatApplication

fun main(args: Array<String>) {
    runApplication<ThermostatApplication>(*args)
}
