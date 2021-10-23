package com.tomclaw.appsend.dto

import com.google.gson.annotations.SerializedName

class AppEntity(
    @SerializedName("app_id")
    val appId: String,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("label")
    val title: String,
    @SerializedName("ver_name")
    val verName: String,
    @SerializedName("ver_code")
    val verCode: Int,
    @SerializedName("size")
    val size: Long,
    @SerializedName("rating")
    val rating: Float,
    @SerializedName("downloads")
    val downloads: Int,
)