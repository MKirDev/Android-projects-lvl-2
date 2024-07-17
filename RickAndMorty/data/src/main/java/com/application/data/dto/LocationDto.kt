package com.application.data.dto

import com.application.domain.entity.Location

data class LocationDto(
    override val name: String,
    override val url: String
) : Location