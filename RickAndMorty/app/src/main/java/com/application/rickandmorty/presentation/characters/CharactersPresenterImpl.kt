package com.application.rickandmorty.presentation.characters

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.application.domain.GetCharactersUseCase
import com.application.rickandmorty.presentation.characters.mappers.CharacterMapper
import com.application.rickandmorty.presentation.characters.models.CharacterModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersPresenterImpl(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val characterMapper: CharacterMapper
) : CharactersPresenter {

    private var view: CharactersView? = null
    private var jobLoadCharacters: Job? = null
    private var jobCollectCharacters: Job? = null
    private var coroutineScopeCached = CoroutineScope(Dispatchers.IO)

    private val _characters = MutableStateFlow<Flow<PagingData<CharacterModel>>?>(null)
    private val characters = _characters.asStateFlow()

    private var charactersCache: Flow<PagingData<CharacterModel>>? = null

    override fun loadCharacters(isFragmentRotated: Boolean) {
        if (!isFragmentRotated) {
            jobLoadCharacters = coroutineScopeCached.launch {
                val characters = getCharactersUseCase.execute().map {
                    it.map { character ->
                        characterMapper.map(character)
                    }
                }.cachedIn(coroutineScopeCached)

                withContext(Dispatchers.Main) {
                    _characters.value = characters
                    charactersCache = characters
                }
            }
        }
        else {
            _characters.value = charactersCache
        }
    }

    override fun onViewDetached() {
        jobLoadCharacters?.cancel()
        jobCollectCharacters?.cancel()
        if (!coroutineScopeCached.isActive) {
            _characters.value = null
            charactersCache = null
        }
        this.view = null
    }

    override fun onViewAttached(view: CharactersView, viewLifecycleOwner: LifecycleOwner) {
        this.view = view

        jobCollectCharacters = viewLifecycleOwner.lifecycleScope.launch {
            characters.collect { flow ->
                flow?.onEach {
                    this@CharactersPresenterImpl.view!!.getCharacters(it)
                }?.launchIn(this)
            }
        }
    }
}