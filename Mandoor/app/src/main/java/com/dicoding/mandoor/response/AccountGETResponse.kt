package com.dicoding.mandoor.response

import com.google.gson.annotations.SerializedName

data class AccountGETResponse(

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("Username")
	val username: String? = null,

	@field:SerializedName("UserID")
	val userID: Int? = null,

	@field:SerializedName("FullName")
	val fullName: String? = null,

	@field:SerializedName("PhoneNumber")
	val phoneNumber: Any? = null,

	@field:SerializedName("Password")
	val password: String? = null,

	@field:SerializedName("Location")
	val location: String? = null
)
