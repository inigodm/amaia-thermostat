package bik.thermostat.common

import kotlin.Throws
import bik.thermostat.exceptions.ThermostatException
import java.io.IOException
import java.io.InputStream
import java.util.*

object ThermostatProperties {
    var p: Properties? = null
    @Throws(ThermostatException::class)
    private fun loadProperties() {
        try {
            p = Properties()
            p!!.load(obtainPropertiesInputStream())
        } catch (e: IOException) {
            e.printStackTrace()
            throw ThermostatException("A problem has occured when opening application.properties")
        }
    }

    @Throws(ThermostatException::class)
    private fun obtainPropertiesInputStream(environment: String = "-${System.getProperty("profile")}"): InputStream {
        return ThermostatProperties::class.java.classLoader.getResourceAsStream("${environment}application.properties")
            ?: throw ThermostatException("application-${environment}.properties file does not exist")
    }

    @JvmStatic
		@Throws(ThermostatException::class)
    operator fun get(key: String?): String {
        if (p == null) {
            loadProperties()
        }
        return p!!.getProperty(key)
    }
}
