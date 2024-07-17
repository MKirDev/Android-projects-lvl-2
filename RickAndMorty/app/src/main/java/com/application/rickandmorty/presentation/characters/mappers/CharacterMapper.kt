package com.application.rickandmorty.presentation.characters.mappers

import com.application.domain.entity.Character
import com.application.rickandmorty.presentation.characters.models.CharacterModel

class CharacterMapper(
    private val locationMapper: LocationMapper,
    private val originMapper: OriginMapper
) {
    fun map(character: Character<*, *>): CharacterModel =
        CharacterModel(
            character.id,
            character.name,
            character.status,
            character.species,
            originMapper.map(character.origin),
            locationMapper.map(character.location),
            character.image
        )
}