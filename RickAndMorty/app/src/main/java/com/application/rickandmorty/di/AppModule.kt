package com.application.rickandmorty.di

import com.application.rickandmorty.presentation.characters.CharactersPresenter
import com.application.rickandmorty.presentation.characters.CharactersPresenterImpl
import com.application.rickandmorty.presentation.characters.mappers.CharacterMapper
import com.application.rickandmorty.presentation.characters.mappers.LocationMapper
import com.application.rickandmorty.presentation.characters.mappers.OriginMapper
import org.koin.dsl.module

val appModule = module {

    single {
        OriginMapper()
    }

    single {
        LocationMapper()
    }

    single {
        CharacterMapper(locationMapper = get(), originMapper = get())
    }

    single<CharactersPresenter> {
        CharactersPresenterImpl(getCharactersUseCase = get(), characterMapper = get())
    }
}