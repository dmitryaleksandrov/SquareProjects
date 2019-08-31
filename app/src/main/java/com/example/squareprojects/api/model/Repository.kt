package com.example.squareprojects.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("stargazers_count")
    val starCount: Int,

    @SerializedName("language")
    val language: String,

    @SerializedName("license")
    val license: License,

    @SerializedName("owner")
    val owner: Owner
) : Parcelable


@Parcelize
data class License(
    @SerializedName("name")
    val name: String
) : Parcelable


@Parcelize
data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String
) : Parcelable
