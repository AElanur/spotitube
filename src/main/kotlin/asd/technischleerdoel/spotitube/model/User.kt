package asd.technischleerdoel.spotitube.model

data class User(
    val user : String,
    val password : String,
    val fName : String? = null
)