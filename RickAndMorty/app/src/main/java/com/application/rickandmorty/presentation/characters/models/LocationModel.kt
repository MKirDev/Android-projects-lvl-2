package com.application.rickandmorty.presentation.characters.models

import com.application.domain.entity.Location

data class LocationModel(
    override val name: String,
    override val url: String
) : com.application.domain.entity.Location