package com.dicoding.mandoor.response

import com.google.gson.annotations.SerializedName

data class MandorResponse(

	@field:SerializedName("MandorResponse")
	val mandorResponse: List<MandorResponseItem?>? = null
)

data class MandorResponseItem(
	@SerializedName("img")
	val img: String?,
	@SerializedName("FullName")
	val fullName: String?,
	@SerializedName("rating_user")
	val ratingUser: Int?,
	@SerializedName("total_proyek")
	val numberProyek: Int?,
	@SerializedName("jangkauan")
	val jangkauan: String?,
	@SerializedName("layanan_lain")
	val layananLain: String?
)

