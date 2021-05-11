package com.davidsantiagoiriarte.presentation.gifslist

import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davidsantiagoiriarte.domain.models.Gif
import com.davidsantiagoiriarte.domain.models.GifsResult
import com.davidsantiagoiriarte.presentation.R
import com.davidsantiagoiriarte.presentation.databinding.FragmentGifsBinding
import com.davidsantiagoiriarte.presentation.errors.Error
import com.davidsantiagoiriarte.presentation.gifslist.adapter.ItemRecyclerViewAdapter
import com.davidsantiagoiriarte.presentation.util.LANDSCAPE_COLUMN_COUNT
import com.davidsantiagoiriarte.presentation.util.PORTRAIT_COLUMN_COUNT
import com.davidsantiagoiriarte.presentation.util.SCREEN_INDEX_ARG
import com.davidsantiagoiriarte.presentation.util.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * A placeholder fragment containing a simple view.
 */
class GifsFragment : Fragment(), FavoriteItemClickListener {

    private var columnCount = PORTRAIT_COLUMN_COUNT

    private val gifsViewModel: GifsViewModel by viewModel {
        parametersOf(SCREEN_INDEX_ARG to requireArguments().getInt(ARG_SECTION_NUMBER))
    }

    private var _binding: FragmentGifsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt(ARG_SECTION_NUMBER)?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGifsBinding.inflate(inflater, container, false)
        val root = binding.root
        val orientation = resources.configuration.orientation

        columnCount = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LANDSCAPE_COLUMN_COUNT
        } else {
            PORTRAIT_COLUMN_COUNT
        }
        with(binding.list) {
            layoutManager = GridLayoutManager(context, columnCount)
            adapter = ItemRecyclerViewAdapter(this@GifsFragment)
        }
        setupScrollListener()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.getString(LAST_SEARCH_QUERY)?.let { query ->
            if (gifsViewModel.itemsResult.value == null) {
                gifsViewModel.searchGifs(query)
                binding.acSearch.setText(query)
            }
        }
        gifsViewModel.itemsResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is GifsResult.Success -> {
                    (binding.list.adapter as? ItemRecyclerViewAdapter)?.submitList(result.gifs) {
                        binding.list.scrollToPosition(0)
                    }
                }
                is GifsResult.Error -> {
                    gifsViewModel.handleException(result.error)
                }
            }
        }

        gifsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        gifsViewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            val message = when (error) {
                is Error.NoConnectionError -> getString(R.string.no_connection_error_message)
                is Error.DefaultError -> "${getString(R.string.default_error_message)} ${error.message}"
            }
            Snackbar
                .make(
                    binding.constraintLayout,
                    message,
                    Snackbar.LENGTH_LONG
                ).show()
        }

        initSearch()
    }

    private fun initSearch() {
        gifsViewModel.recentSearchLiveData.observe(viewLifecycleOwner) { queriesList ->
            binding.acSearch.setAdapter(
                context?.let {
                    ArrayAdapter<String>(
                        it,
                        R.layout.autocomplete_list_item,
                        R.id.text_view_list_item,
                        queriesList
                    )
                }
            )
        }
        binding.acSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateListFromSearchQuery()
                view?.let { context?.hideKeyboard(it) }
                true
            } else {
                false
            }
        }
        binding.acSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateListFromSearchQuery()
                view?.let { context?.hideKeyboard(it) }
                true
            } else {
                false
            }
        }
    }

    private fun updateListFromSearchQuery() {
        binding.acSearch.text.trim().let {
            if (it.isNotEmpty()) {
                binding.list.scrollToPosition(0)
                gifsViewModel.searchGifs(it.toString())
            }
        }
    }

    private fun setupScrollListener() {
        val layoutManager = binding.list.layoutManager as GridLayoutManager
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                gifsViewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        const val LAST_SEARCH_QUERY = "LAST_SEARCH_QUERY"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): GifsFragment {
            return GifsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFavoriteClicked(gif: Gif) {
        gifsViewModel.favoriteGifClicked(gif)
    }
}