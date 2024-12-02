package com.dicoding.mandoor.response

import com.google.gson.annotations.SerializedName

data class RegUserResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null
)
