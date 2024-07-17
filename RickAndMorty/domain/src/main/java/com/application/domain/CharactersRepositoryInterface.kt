package com.application.domain

import androidx.paging.PagingData
import com.application.domain.entity.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepositoryInterface {
    fun getPagedCharacters(): Flow<PagingData<Character<*, *>>>
}