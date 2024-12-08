package com.dicoding.mandoor.response

import com.google.gson.annotations.SerializedName

data class SurveyGETResponse(

	@field:SerializedName("SurveyGETResponse")
	val surveyGETResponse: List<SurveyGETResponseItem?>? = null
)

data class SurveyGETResponseItem(

	@field:SerializedName("Alamat")
	val alamat: String? = null,

	@field:SerializedName("SurveyID")
	val surveyID: Int? = null,

	@field:SerializedName("Foto")
	val foto: String? = null,

	@field:SerializedName("UserID")
	val userID: Int? = null,

	@field:SerializedName("Deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("Budget")
	val budget: String? = null,

	@field:SerializedName("FilteredMandors")
	val filteredMandors: String? = null,

	@field:SerializedName("Tanggal")
	val tanggal: String? = null
)
