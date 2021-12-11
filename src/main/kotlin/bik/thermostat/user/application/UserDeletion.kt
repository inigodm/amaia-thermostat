package bik.thermostat.user.application

import bik.thermostat.user.infraestructure.UserManager
import org.springframework.stereotype.Component

@Component
class UserDeletion(var userManager: UserManager) {
    fun delete(userName : String) {
        userManager.deleteUser(userName)
    }
}
