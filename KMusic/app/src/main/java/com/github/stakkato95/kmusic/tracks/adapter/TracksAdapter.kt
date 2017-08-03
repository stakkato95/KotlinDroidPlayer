package com.github.stakkato95.kmusic.tracks.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.tracks.Track

/**
 * Created by artsiomkaliaha on 03.08.17.
 */
class TracksAdapter(private val tracks: List<Track>) : RecyclerView.Adapter<TracksViewHolder>() {

    override fun onBindViewHolder(holder: TracksViewHolder?, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track_info, parent, false)
        return TracksViewHolder(view)
    }

    override fun getItemCount() = tracks.size
}

class TracksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}