package com.application.rickandmorty.presentation.characters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.rickandmorty.R
import com.application.rickandmorty.databinding.CharacterItemBinding
import com.application.rickandmorty.presentation.characters.models.CharacterModel
import com.bumptech.glide.Glide

class CharacterPagedListAdapter(
    private val onClick: (CharacterModel) -> Unit
) : PagingDataAdapter<CharacterModel, CharacterViewHolder>(
    DiffUtilCallback()
) {

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            name.text = item?.name
            statusAndSpecies.text = StringBuilder().append("${item?.status} - ${item?.species}")
            location.text = item?.location?.name
            item?.let {
                Glide
                    .with(image.context)
                    .load(item.image)
                    .into(image)

                when (it.status) {
                    "Alive" -> holder.binding.imageStatus.setImageResource(R.drawable.green_circle_24)
                    "Dead" -> holder.binding.imageStatus.setImageResource(R.drawable.red_circle_24)
                    else -> holder.binding.imageStatus.setImageResource(R.drawable.gray_circle_24)
                }
            }

            holder.binding.cardView.setOnClickListener {
                item?.let {
                    onClick(it)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class CharacterViewHolder(val binding: CharacterItemBinding): RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback: DiffUtil.ItemCallback<CharacterModel>() {
    override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean =
        oldItem == newItem

}