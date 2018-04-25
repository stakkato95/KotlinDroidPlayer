package com.github.stakkato95.kmusic.screen.tracks.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.mvp.repository.model.Track
import com.github.stakkato95.kmusic.util.extensions.picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_track_info.*

/**
 * Created by artsiomkaliaha on 03.08.17.
 */
class TracksAdapter(private var tracks: List<Track>? = null) : RecyclerView.Adapter<TracksViewHolder>() {

    override fun onBindViewHolder(holder: TracksViewHolder?, position: Int) {
        val trcks = tracks
        trcks?.let { holder?.setup(trcks[position]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track_info, parent, false)
        return TracksViewHolder(view)
    }

    override fun getItemCount() = tracks?.size ?: 0

    fun updateTracks(tracks: List<Track>?) {
        this.tracks = tracks
        notifyDataSetChanged()
    }
}

class TracksViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun setup(track: Track) {
        trackNameView.text = track.name
        trackAuthorView.text = track.author

//        val retreiver = MediaMetadataRetriever()
//        retreiver.setDataSource(track.path)
//        retreiver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM).toInt()

        trackImage.context.picasso.load(Uri.parse("file://${track.albumId}")).into(trackImage)
    }
}