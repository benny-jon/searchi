package com.bennyjon.searchi.adapter

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import com.bennyjon.searchi.R
import com.bennyjon.searchi.models.FlickrPhoto
import com.bennyjon.searchi.network.getPhotoUrl
import com.bennyjon.searchi.adapter.PhotosAdapter.PhotoHolder
import com.squareup.picasso.Picasso
import androidx.recyclerview.widget.DiffUtil

/**
 * Recycler View Adapter for the list of {@link FlickrPhoto}.
 */
class PhotosAdapter : PagingDataAdapter<FlickrPhoto, PhotoHolder>(DefaultDiff()) {

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

    class DefaultDiff : DiffUtil.ItemCallback<FlickrPhoto>() {
        override fun areItemsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean {
            return oldItem == newItem
        }
    }
}
