package com.dicoding.mandoor.response

import com.google.gson.annotations.SerializedName

data class AccountDeleteResponse(

	@field:SerializedName("message")
	val message: String? = null
)
