package com.application.rickandmorty.presentation.characters

import androidx.paging.PagingData
import com.application.rickandmorty.presentation.characters.models.CharacterModel

interface CharactersView {
    fun getCharacters(characters: PagingData<CharacterModel>?)
}