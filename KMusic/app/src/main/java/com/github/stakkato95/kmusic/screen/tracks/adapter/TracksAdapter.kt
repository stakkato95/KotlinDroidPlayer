package com.github.stakkato95.kmusic.screen.tracks.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack
import com.github.stakkato95.kmusic.util.extensions.loadCover
import com.github.stakkato95.kmusic.util.extensions.picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_track_info.*

/**
 * Created by artsiomkaliaha on 03.08.17.
 */
class TracksAdapter(private var playerTracks: List<PlayerTrack>? = null) : RecyclerView.Adapter<TracksViewHolder>() {

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        val tracks = playerTracks
        tracks?.let { holder.setup(tracks[position]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track_info, parent, false)
        return TracksViewHolder(view)
    }

    override fun getItemCount() = playerTracks?.size ?: 0

    fun updateTracks(playerTracks: List<PlayerTrack>?) {
        this.playerTracks = playerTracks
        notifyDataSetChanged()
    }
}

class TracksViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun setup(playerTrack: PlayerTrack) {
        trackNameView.text = playerTrack.name
        trackAuthorView.text = playerTrack.author
        trackImage.context.picasso
                .loadCover(playerTrack.coverPath)
                .error(R.drawable.test_background)
                .into(trackImage)
    }
}