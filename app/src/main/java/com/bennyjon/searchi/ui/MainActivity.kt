package com.bennyjon.searchi.ui

import android.app.SearchManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import android.view.Menu
import android.view.View
import android.widget.SearchView
import com.bennyjon.searchi.R
import com.bennyjon.searchi.adapter.PhotosAdapter
import com.bennyjon.searchi.data.FlickrPhotoViewModel
import com.bennyjon.searchi.network.FlickrApi
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PARAM_QUERY : String = "PARAM_QUERY"
    }

    @Inject internal lateinit var flickrApi: FlickrApi

    private lateinit var photosAdapter: PhotosAdapter
    private lateinit var viewModel : FlickrPhotoViewModel

    private var currentQuery : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        photosAdapter = PhotosAdapter()
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = photosAdapter
        recyclerView.setHasFixedSize(true)

        viewModel = ViewModelProviders.of(this).get(FlickrPhotoViewModel::class.java)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(PARAM_QUERY, currentQuery)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        currentQuery = savedInstanceState?.getString(PARAM_QUERY, null)
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

        emptyScreenText.visibility = View.GONE
        searchLoading.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE

        viewModel.getPhotos(flickrApi, currentQuery!!, resetData)
                .observe(this, Observer { photos ->
                    photosAdapter.submitList(photos)

                    if (photos != null && photos.isNotEmpty()) {
                        searchLoading.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    } else {
                        emptyScreenText.visibility = View.VISIBLE
                        searchLoading.visibility = View.GONE
                        recyclerView.visibility = View.GONE
                    }
                })
    }
}
