package com.application.rickandmorty.presentation.characters

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.application.rickandmorty.R
import com.application.rickandmorty.databinding.FragmentCharactersBinding
import com.application.rickandmorty.presentation.characters.CharactersView
import com.application.rickandmorty.presentation.characters.models.CharacterModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import java.util.ArrayList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

class CharactersFragment : Fragment(), CharactersView {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private val bundle = Bundle()
    private var isFragmentRotated = false
    private var isClick = false

    private val charactersPresenterImpl: CharactersPresenter by inject()

    private val pagedAdapter = CharacterPagedListAdapter { characterModel ->
        onItemClick(characterModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            isFragmentRotated = it.getBoolean(ARG_PARAM2)
            isClick = it.getBoolean(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        charactersPresenterImpl.onViewAttached(this, viewLifecycleOwner)

        binding.recyclerView.adapter = pagedAdapter.withLoadStateFooter(CustomLoadStateAdapter())

        binding.swipeRefresh.setOnRefreshListener {
            charactersPresenterImpl.loadCharacters(false)
        }

        if (!isClick) charactersPresenterImpl.loadCharacters(isFragmentRotated)

        pagedAdapter.addLoadStateListener { loadState ->
            binding.swipeRefresh.isRefreshing = loadState.refresh is LoadState.Loading

            if (loadState.refresh is LoadState.Error || loadState.append is LoadState.Error) {
                Toast.makeText(context, "Data update error. Swipe down to try again", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getCharacters(characters: PagingData<CharacterModel>?) {
        if (characters != null) {
            pagedAdapter.submitData(viewLifecycleOwner.lifecycle, characters)
        }
    }

    private fun onItemClick(item: CharacterModel) {
        isClick = true
        bundle.putStringArrayList(
            ARG_PARAM1,
            arrayListOf(item.name, item.image, item.species, item.location.name, item.origin.name)
        )
        if (binding.swipeRefresh.isRefreshing) binding.swipeRefresh.isRefreshing = false
        findNavController().navigate(
            R.id.action_charactersFragment_to_additionalInfoFragment,
            bundle
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(ARG_PARAM2, true)
        outState.putBoolean(ARG_PARAM3, isClick)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        if (!requireActivity().isChangingConfigurations) {
            charactersPresenterImpl.onViewDetached()
        }
    }

}