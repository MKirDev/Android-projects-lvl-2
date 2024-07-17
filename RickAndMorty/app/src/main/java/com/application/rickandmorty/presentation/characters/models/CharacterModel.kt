package com.application.rickandmorty.presentation.characters.models

import com.application.domain.entity.Character

data class CharacterModel(
    override val id: Int,
    override val name: String,
    override val status: String,
    override val species: String,
    override val origin: OriginModel,
    override val location: LocationModel,
    override val image: String
) : Character<LocationModel, OriginModel>
