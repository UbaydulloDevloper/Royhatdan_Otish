package Models

class Users {
    var id: Int? = null
    var name: String? = null
    var imagePath: ByteArray? = null
    var number: String? = null
    var country: String? = null
    var city: String? = null
    var password: String? = null


    constructor(number: String?, password: String?) {
        this.name = name
        this.number = number
        this.password = password

    }

    constructor()
    constructor(
        id: Int?,
        name: String?,
        imagePath: ByteArray?,
        number: String?,
        country: String?,
        city: String?,
        password: String?
    ) {
        this.id = id
        this.name = name
        this.imagePath = imagePath
        this.number = number
        this.country = country
        this.city = city
        this.password = password
    }

    constructor(
        name: String?,
        imagePath: ByteArray?,
        number: String?,
        country: String?,
        city: String?,
        password: String?
    ) {
        this.name = name
        this.imagePath = imagePath
        this.number = number
        this.country = country
        this.city = city
        this.password = password
    }
}
