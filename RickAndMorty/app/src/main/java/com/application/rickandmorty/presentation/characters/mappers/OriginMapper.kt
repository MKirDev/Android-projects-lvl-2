package com.application.rickandmorty.presentation.characters.mappers

import com.application.domain.entity.Origin
import com.application.rickandmorty.presentation.characters.models.OriginModel

class OriginMapper {
    fun map(origin: com.application.domain.entity.Origin): OriginModel =
        OriginModel(
            origin.name
        )
}