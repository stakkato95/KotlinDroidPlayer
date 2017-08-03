package com.github.stakkato95.kmusic.tracks

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.tracks.adapter.TracksAdapter
import kotlinx.android.synthetic.main.fragment_tracks.view.*

/**
 * Created by artsiomkaliaha on 14.07.17.
 */
class TracksFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tracks, container, false)
        with(view.tracksRecyclerView) {
            adapter = TracksAdapter(getTracks())
            layoutManager = GridLayoutManager(activity, 2)
        }
        return view
    }

    fun getTracks(): List<Track> {
        val tracks = arrayListOf<Track>()
        for (i in 0..20) {
            tracks.add(Track("Need for Speed", "Lil John"))
        }

        return tracks
    }
}