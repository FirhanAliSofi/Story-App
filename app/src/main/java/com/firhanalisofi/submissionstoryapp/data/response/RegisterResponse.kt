package com.firhanalisofi.submissionstoryapp.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterResponse(
	val error: Boolean,
	val message: String
) : Parcelable