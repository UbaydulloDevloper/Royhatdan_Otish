package DB

import Models.Users

interface InterfaceDB {
    fun addUser(users: Users)
    fun deleteUser(users: Users)
    fun getAllImage(): List<Users>
}