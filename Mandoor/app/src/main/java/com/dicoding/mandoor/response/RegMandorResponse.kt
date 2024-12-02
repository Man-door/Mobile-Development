package com.dicoding.mandoor.response

import com.google.gson.annotations.SerializedName

data class RegMandorResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("mandorId")
	val mandorId: String? = null
)
