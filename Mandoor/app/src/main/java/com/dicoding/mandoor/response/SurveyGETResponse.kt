package com.dicoding.mandoor.response

import com.google.gson.annotations.SerializedName

data class SurveyGETResponse(

	@field:SerializedName("SurveyGETResponse")
	val surveyGETResponse: List<SurveyGETResponseItem?>? = null
)

data class SurveyGETResponseItem(

	@field:SerializedName("Alamat")
	val alamat: String? = null,

	@field:SerializedName("Foto")
	val foto: String? = null,

	@field:SerializedName("Deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("Budget")
	val budget: String? = null,

	@field:SerializedName("Tanggal")
	val tanggal: String? = null
)
