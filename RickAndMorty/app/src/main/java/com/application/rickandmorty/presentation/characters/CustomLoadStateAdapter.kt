package com.application.rickandmorty.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.application.rickandmorty.databinding.LoadStateBinding

class CustomLoadStateAdapter : LoadStateAdapter<CustomLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: CustomLoadStateViewHolder, loadState: LoadState) = Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CustomLoadStateViewHolder {
        return CustomLoadStateViewHolder(
            LoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class CustomLoadStateViewHolder(binding: LoadStateBinding): RecyclerView.ViewHolder(binding.root)