package br.com.programadorthi.compose.models

import androidx.compose.Model

@Model
data class AppTitle(
    var topText: String = "",
    var bottomText: String = ""
)