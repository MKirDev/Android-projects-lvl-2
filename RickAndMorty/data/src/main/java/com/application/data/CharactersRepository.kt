package com.application.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.application.domain.CharactersRepositoryInterface
import com.application.domain.entity.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharactersRepository(private val charactersPagingSource: CharactersPagingSource): CharactersRepositoryInterface  {

    override fun getPagedCharacters(): Flow<PagingData<Character<*, *>>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { charactersPagingSource }
        ).flow.map { pagingData ->
            pagingData.map { it }
        }
    }
}