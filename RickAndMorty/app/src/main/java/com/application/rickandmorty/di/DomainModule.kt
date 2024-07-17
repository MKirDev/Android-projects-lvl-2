package com.application.rickandmorty.di

import org.koin.dsl.module

val domainModule = module {

    factory {
        com.application.domain.GetCharactersUseCase(charactersRepositoryInterface = get())
    }

}