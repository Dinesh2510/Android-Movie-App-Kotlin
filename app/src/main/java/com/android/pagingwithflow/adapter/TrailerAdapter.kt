package com.android.pagingwithflow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.pagingwithflow.R
import com.android.pagingwithflow.databinding.ItemTrailerBinding
import com.android.pagingwithflow.model.TrailerResultList
import com.android.pagingwithflow.network.NetworkingConstants
import com.android.pagingwithflow.network.NetworkingConstants.YOUTUBE_THUMBNAIL_URL_ENDPOINT


class TrailerAdapter(
    var trailerList: List<TrailerResultList>,
    val trailerOnClick: (TrailerResultList) -> Unit
) : RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {
    class TrailerViewHolder(val binding: ItemTrailerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(trailer: TrailerResultList, trailerOnClick: (TrailerResultList) -> Unit) {
            itemView.setOnClickListener { trailerOnClick(trailer) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemTrailerBinding>(
            inflater,
            R.layout.item_trailer, parent, false
        )
        return TrailerViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return trailerList.size
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        holder.binding.trailer = trailerList[position]
        holder.bind(trailerList[position], trailerOnClick)
        holder.binding.itemVideoThumbnail.load(NetworkingConstants.YOUTUBE_THUMBNAIL_URL + trailerList[position].key + YOUTUBE_THUMBNAIL_URL_ENDPOINT)

    }
}