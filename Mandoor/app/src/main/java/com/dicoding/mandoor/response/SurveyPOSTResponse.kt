package com.dicoding.mandoor.response

import com.google.gson.annotations.SerializedName

data class SurveyPOSTResponse(

	@field:SerializedName("FilteredMandors")
	val filteredMandors: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
