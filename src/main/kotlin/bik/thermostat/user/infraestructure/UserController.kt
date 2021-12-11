package bik.thermostat.user.infraestructure

import bik.thermostat.user.application.UserCreation
import bik.thermostat.user.application.UserDeletion
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping(value = ["/user"], consumes = [MediaType.APPLICATION_JSON_VALUE])
class UserController(var userCreation: UserCreation,
            var userDeletion: UserDeletion
) {

    @PostMapping()
    fun createUser(@RequestBody userRequest: UserRequest) {
        println("Requesting to create user $userRequest")
        userCreation.create(userRequest.username, userRequest.password)
    }

    @DeleteMapping()
    fun deleteUser(@RequestBody userDeletionRequest : UserDeletionRequest) {
        println("Requesting to delete user $userDeletionRequest")
        userDeletion.delete(userDeletionRequest.username)
    }
}

data class UserRequest (val username: String?, val password: String?)
data class UserDeletionRequest(val username: String)
