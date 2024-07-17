package com.application.rickandmorty.presentation.characters.models

import com.application.domain.entity.Origin

data class OriginModel(
    override val name: String
) : com.application.domain.entity.Origin
