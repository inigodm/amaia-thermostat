package bik.thermostat.user.application

import bik.thermostat.user.infraestructure.UserManager
import org.springframework.stereotype.Component

@Component
class UserCreation(var userManager: UserManager) {
    fun create(username: String?, password: String?) {
        if (username == null || password == null) {
            throw Exception("User and password should not be void");
        }
        userManager.createUser(username, password)
    }
}
