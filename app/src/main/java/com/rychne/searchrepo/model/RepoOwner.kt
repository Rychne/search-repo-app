package com.rychne.searchrepo.model

import com.google.gson.annotations.SerializedName

data class RepoOwner(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val login: String,
    val url: String,
)
