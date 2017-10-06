package com.github.stakkato95.kmusic.screen.tracks.ui

import android.arch.lifecycle.LifecycleObserver
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stakkato95.kmusic.App
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.common.BaseFragment
import com.github.stakkato95.kmusic.mvp.presenter.TracksPresenter
import com.github.stakkato95.kmusic.mvp.repository.model.Track
import com.github.stakkato95.kmusic.screen.tracks.adapter.TracksAdapter
import kotlinx.android.synthetic.main.fragment_tracks.view.*
import javax.inject.Inject

/**
 * Created by artsiomkaliaha on 14.07.17.
 */
class TracksFragment : BaseFragment() {

    @Inject
    lateinit var presenter: TracksPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(presenter)
    }

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
            tracks.add(Track("Need for Speed", "Lil John", ""))
        }

        return tracks
    }

    override fun injectPresenter(): LifecycleObserver {
        App.INJECTOR.plusAllTracksComponent()?.inject(this)
        return presenter
    }
}