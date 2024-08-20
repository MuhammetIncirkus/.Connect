package com.incirkus.connect.DATA.Model

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "current_user")
data class CurrentUserDatatype(
    @PrimaryKey
    val userId: String,
    val firstName: String?,
    var lastName: String?,
    val fullName: String?,
    var image: String?,
    var email: String?,
    var phoneNumber: String?,
    var department: String?,
) {
    // Funktion zur Konvertierung von User zu CurrentUser
    companion object {
        fun userToCurrentUser(user: User): CurrentUserDatatype {
            return CurrentUserDatatype(
                userId = user.userId,
                firstName = user.firstName,
                lastName = user.lastName,
                fullName = user.fullName,
                image = user.image,
                email = user.email,
                phoneNumber = user.phoneNumber,
                department = user.department
            )
        }
    }

    // Funktion zur Konvertierung von CurrentUser zu User
    fun currentUserToUser(): User {
        return User(
            userId = this.userId,
            firstName = this.firstName,
            lastName = this.lastName,
            fullName = this.fullName,
            image = this.image,
            email = this.email,
            phoneNumber = this.phoneNumber,
            department = this.department
        )
    }
}
