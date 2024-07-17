package com.application.rickandmorty.presentation.characters

import androidx.lifecycle.LifecycleOwner
import com.application.rickandmorty.presentation.characters.CharactersView
import kotlinx.coroutines.CoroutineScope

interface CharactersPresenter {
    fun loadCharacters(isFragmentRotated: Boolean)
    fun onViewDetached()
    fun onViewAttached(view: CharactersView, viewLifecycleOwner: LifecycleOwner)
}