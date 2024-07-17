package com.application.data.dto

import com.application.domain.entity.Character

data class CharacterDto(
    override val id: Int,
    override val name: String,
    override val status: String,
    override val species: String,
    override val origin: OriginDto,
    override val location: LocationDto,
    override val image: String
) : Character<LocationDto, OriginDto>