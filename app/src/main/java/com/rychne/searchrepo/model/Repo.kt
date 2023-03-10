package com.rychne.searchrepo.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Repo(
    val owner: RepoOwner,
    val name: String,
    val description: String?,
    val link: String?,
    @SerializedName("stargazers_count")
    val stars: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date
)
