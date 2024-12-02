package com.dicoding.mandoor.response

import com.google.gson.annotations.SerializedName

data class LogMandorResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
