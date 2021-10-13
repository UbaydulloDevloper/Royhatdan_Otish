package DB

import Models.Users
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    InterfaceDB {
    companion object {
        const val DB_NAME = "registration"
        const val DB_VERSION = 1

        const val TABLE_DB = "registrationUsers"
        const val ID = "id"
        const val NAME = "name"

        const val IMAGE_PATH = "image_path"
        const val NUMBER = "number"
        const val COUNTRY = "country"
        const val CITY = "city"
        const val PASSWORD = "password"


    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table $TABLE_DB($ID integer primary key autoincrement not null, $NAME text not null, $IMAGE_PATH blob not null, $NUMBER text not null, $COUNTRY text not null, $CITY Text not null, $PASSWORD Text not null)")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun addUser(users: Users) {
        val database = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(NAME, users.name) // User's name
        contentValue.put(IMAGE_PATH, users.imagePath) // image path
        contentValue.put(NUMBER, users.number) //Phone number
        contentValue.put(COUNTRY, users.country) // from which country
        contentValue.put(CITY, users.city) // from which city
        contentValue.put(PASSWORD, users.password) // password user's
        database.insert(TABLE_DB, null, contentValue)
        database.close()
    }

    override fun deleteUser(users: Users) {
        val database = this.writableDatabase
        database.delete(TABLE_DB, "$ID = ?", arrayOf(users.id.toString()))
        database.close()
    }

    @SuppressLint("Recycle")
    override fun getAllImage(): List<Users> {
        val list = ArrayList<Users>()
        val database = this.readableDatabase
        val cursor = database.rawQuery("select * from $TABLE_DB", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val imagePath = cursor.getBlob(2)
                val number = cursor.getString(3)
                val country = cursor.getString(4)
                val city = cursor.getString(5)
                val password = cursor.getString(6)
                list.add(Users(id, name, imagePath, number, country, city, password))
            } while (cursor.moveToNext())
        }
        return list
    }
}