package bik.thermostat.exceptions

import java.lang.Exception

class ThermostatException : Exception {
    constructor(message: String?, e: Exception?) : super(message, e) {}
    constructor(message: String?) : super(message) {}
}

class ThermostatError : RuntimeException {
    constructor(message: String?, e: Exception?) : super(message, e) {}
    constructor(message: String?) : super(message) {}
}
