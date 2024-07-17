package com.application.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.application.data.dto.CharacterDto
import kotlinx.coroutines.delay

class CharactersPagingSource(
    private val charactersDataSource: CharactersDataSource
) : PagingSource<Int, CharacterDto>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterDto>): Int =
        FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDto> {
        val page = params.key ?: 1
        return kotlin.runCatching {
            delay(1000)
            charactersDataSource.getRetrofitInstance().charactersList.getCharactersList(page).results
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}