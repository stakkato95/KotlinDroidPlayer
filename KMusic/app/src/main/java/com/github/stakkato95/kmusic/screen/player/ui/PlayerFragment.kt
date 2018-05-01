package com.github.stakkato95.kmusic.screen.player.ui

import android.arch.lifecycle.LifecycleObserver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stakkato95.kmusic.App
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.common.BaseFragment
import com.github.stakkato95.kmusic.mvp.presenter.PlayerPresenter
import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack
import com.github.stakkato95.kmusic.mvp.view.PlayerView
import com.github.stakkato95.kmusic.screen.player.adapter.PlayerButtonPagerAdapter
import kotlinx.android.synthetic.main.fragment_player.*
import javax.inject.Inject

/**
 * Created by artsiomkaliaha on 14.07.17.
 */
class PlayerFragment : BaseFragment(), PlayerView, PlayerScreen {

    @Inject
    lateinit var presenter: PlayerPresenter

    private var lastVisiblePlayerButton: PlayerButton? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun injectPresenter(): LifecycleObserver {
        App.INJECTOR.plusPlayerComponent(this)?.inject(this)
        return presenter
    }

    override fun showTracks(playerTracks: List<PlayerTrack>) {
        with(pager) {
            adapter = PlayerButtonPagerAdapter(
                    childFragmentManager,
                    playerTracks.size,
                    { progress -> presenter.rewind(progress) },
                    { trackOrdinal -> presenter.playPause(trackOrdinal) }
            )
            setCurrentItemObserver { old, new -> if (old > new) presenter.previousTrack() else presenter.nextTrack() }

            val leftRightPadding = resources.displayMetrics.widthPixels / 6
            setPadding(leftRightPadding, 0, leftRightPadding, 0)
        }
    }

    override fun showNoTracks() {
        //TODO
    }

    override fun showError() {
        //TODO
    }

    override fun updateCurrentTrackProgress(progress: Float) {
        lastVisiblePlayerButton?.setProgress(progress)
    }

    override fun setLastVisiblePlayerButton(playerButton: PlayerButton) { lastVisiblePlayerButton = playerButton }
}