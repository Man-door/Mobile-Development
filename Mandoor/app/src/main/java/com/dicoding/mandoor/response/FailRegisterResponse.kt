package com.dicoding.mandoor.response

import com.google.gson.annotations.SerializedName

data class FailRegisterResponse(

	@field:SerializedName("error")
	val error: String? = null
)
