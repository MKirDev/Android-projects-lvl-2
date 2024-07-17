package com.application.rickandmorty.presentation.characters.mappers

import com.application.domain.entity.Location
import com.application.rickandmorty.presentation.characters.models.LocationModel

class LocationMapper {
    fun map(location: com.application.domain.entity.Location): LocationModel =
        LocationModel(
            location.name,
            location.url
        )
}