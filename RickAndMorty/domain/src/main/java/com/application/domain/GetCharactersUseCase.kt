package com.application.domain

import androidx.paging.PagingData
import com.application.domain.entity.Character
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase(private val charactersRepositoryInterface: CharactersRepositoryInterface) {
    fun execute(): Flow<PagingData<Character<*, *>>> {
        return charactersRepositoryInterface.getPagedCharacters()
    }
}