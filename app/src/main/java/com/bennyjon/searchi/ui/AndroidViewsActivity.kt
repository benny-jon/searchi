package com.bennyjon.searchi.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.bennyjon.searchi.R
import com.bennyjon.searchi.adapter.PhotosAdapter
import com.bennyjon.searchi.data.FlickrPhotoViewModel
import com.bennyjon.searchi.databinding.ActivityAndroidViewsBinding
import com.bennyjon.searchi.network.FlickrApi
import dagger.android.AndroidInjection
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AndroidViewsActivity: AppCompatActivity() {

    companion object {
        private const val PARAM_QUERY : String = "PARAM_QUERY"
    }

    private lateinit var binding: ActivityAndroidViewsBinding

    @Inject
    internal lateinit var flickrApi: FlickrApi

    private lateinit var photosAdapter: PhotosAdapter
    private lateinit var viewModel : FlickrPhotoViewModel

    private var currentQuery : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityAndroidViewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        photosAdapter = PhotosAdapter()
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = photosAdapter
        binding.recyclerView.setHasFixedSize(true)

        viewModel = ViewModelProvider(this).get(FlickrPhotoViewModel::class.java)

        // TODO Add Loading indicators and Retry buttons as Footers of the Recycler view
        //      to use this nice built-in LoadState listener in PagingV3.
        binding.searchLoading.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        photosAdapter.addLoadStateListener { combinedLoadStates ->
            when {
                combinedLoadStates.refresh is LoadState.Loading -> {
                    binding.emptyScreenText.visibility = View.GONE
                    binding.searchLoading.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                combinedLoadStates.append.endOfPaginationReached -> {
                    binding.searchLoading.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }
                else -> {
                    binding.searchLoading.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PARAM_QUERY, currentQuery)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentQuery = savedInstanceState.getString(PARAM_QUERY, null)
        fetchCurrentQuery()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            currentQuery = intent.getStringExtra(SearchManager.QUERY)
            fetchCurrentQuery(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val menuItem = menu?.findItem(R.id.app_bar_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        if (currentQuery != null) {
            menuItem.expandActionView()
            searchView.setQuery(currentQuery, false)
        }
        return true
    }

    private fun fetchCurrentQuery(resetData : Boolean = false) {
        if (currentQuery == null) return

        binding.emptyScreenText.visibility = View.GONE
        binding.searchLoading.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE

        viewModel.getPhotos(flickrApi, currentQuery!!, resetData)
            .observe(this) { photos ->
                photosAdapter.submitData(lifecycle, photos)
            }

    }
}