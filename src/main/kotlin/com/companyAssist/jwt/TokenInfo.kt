package com.companyAssist.jwt

data class TokenInfo(
    val grantType: String,
    val accessToken: String
)