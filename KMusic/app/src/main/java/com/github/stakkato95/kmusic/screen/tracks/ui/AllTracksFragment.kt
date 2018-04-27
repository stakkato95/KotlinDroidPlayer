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
import com.github.stakkato95.kmusic.mvp.presenter.AllTracksPresenter
import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack
import com.github.stakkato95.kmusic.mvp.view.AllTracksView
import com.github.stakkato95.kmusic.screen.tracks.adapter.TracksAdapter
import kotlinx.android.synthetic.main.fragment_tracks.*
import kotlinx.android.synthetic.main.fragment_tracks.view.*
import javax.inject.Inject

/**
 * Created by artsiomkaliaha on 14.07.17.
 */
class AllTracksFragment : BaseFragment(), AllTracksView {

    @Inject
    lateinit var presenter: AllTracksPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tracks, container, false)
        with(view.tracksRecyclerView) {
            adapter = TracksAdapter(null)
            layoutManager = GridLayoutManager(activity, 2)
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        App.INJECTOR.clearAllTracksComponent()
    }

    override fun injectPresenter(): LifecycleObserver {
        App.INJECTOR.plusAllTracksComponent(this)?.inject(this)
        return presenter
    }

    override fun showTracks(playerTracks: List<PlayerTrack>) {
        (tracksRecyclerView.adapter as TracksAdapter).updateTracks(playerTracks)
    }

    override fun showNoTracks() {
        //TODO
    }

    override fun showError() {
        //TODO
    }

    override fun updateCurrentTrackProgress(progress: Float) {
        //TODO
    }
}