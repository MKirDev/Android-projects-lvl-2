package com.application.data.dto

import com.application.domain.entity.Origin

data class OriginDto(
    override val name: String
) : Origin
