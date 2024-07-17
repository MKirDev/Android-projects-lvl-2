package com.application.data.dto

import com.application.domain.entity.Results

data class ResultsDto(
    override val results: List<CharacterDto>
) : Results<CharacterDto>