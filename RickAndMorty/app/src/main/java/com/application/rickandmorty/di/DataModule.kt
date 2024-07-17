package com.application.rickandmorty.di

import com.application.data.CharactersDataSource
import com.application.data.CharactersPagingSource
import com.application.data.CharactersRepository
import com.application.domain.CharactersRepositoryInterface
import org.koin.dsl.module

val dataModule = module {

    single {
        CharactersDataSource()
    }

    single {
        CharactersPagingSource(charactersDataSource = get())
    }

    single<CharactersRepositoryInterface> {
        CharactersRepository(charactersPagingSource = get())
    }
}