package com.dicoding.mandoor.response

import com.google.gson.annotations.SerializedName

data class AccountPUTResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null
)

data class User(

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("Username")
	val username: String? = null,

	@field:SerializedName("UserID")
	val userID: Int? = null,

	@field:SerializedName("FullName")
	val fullName: String? = null,

	@field:SerializedName("PhoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("Password")
	val password: String? = null,

	@field:SerializedName("Location")
	val location: Any? = null
)
