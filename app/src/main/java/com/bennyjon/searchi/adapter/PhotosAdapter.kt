package com.bennyjon.searchi.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bennyjon.searchi.R
import com.bennyjon.searchi.models.FlickrPhoto
import com.bennyjon.searchi.network.getPhotoUrl
import com.bennyjon.searchi.adapter.PhotosAdapter.PhotoHolder
import com.squareup.picasso.Picasso
import android.support.v7.util.DiffUtil

/**
 * Recycler View Adapter for the list of {@link FlickrPhoto}.
 */
class PhotosAdapter : PagedListAdapter<FlickrPhoto, PhotoHolder>(getFlickrPhotoDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PhotoHolder(inflater.inflate(R.layout.photo_item, null))
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val photo: FlickrPhoto = getItem(position) ?: return
        holder.image?.let {
            Picasso.get()
                    .load(getPhotoUrl(photo.farm, photo.server, photo.id, photo.secret))
                    .fit()
                    .centerCrop()
                    .into(it)
        }
    }

    class PhotoHolder(itemView: View) : ViewHolder(itemView) {
        var image: ImageView? = null
        init {
            image = itemView.findViewById(R.id.image)
        }
    }

    companion object {
        fun getFlickrPhotoDiff() = object : DiffUtil.ItemCallback<FlickrPhoto>() {
            override fun areItemsTheSame(oldItem: FlickrPhoto?, newItem: FlickrPhoto?): Boolean {
                return oldItem?.id.equals(newItem?.id)
            }

            override fun areContentsTheSame(oldItem: FlickrPhoto?, newItem: FlickrPhoto?): Boolean {
                return oldItem == newItem
            }
        }
    }
}
