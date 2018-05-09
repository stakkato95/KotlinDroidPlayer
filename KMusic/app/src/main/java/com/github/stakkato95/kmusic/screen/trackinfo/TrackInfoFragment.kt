package com.github.stakkato95.kmusic.screen.trackinfo

import android.arch.lifecycle.LifecycleObserver
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stakkato95.kmusic.App
import com.github.stakkato95.kmusic.R
import com.github.stakkato95.kmusic.common.BaseFragment
import com.github.stakkato95.kmusic.mvp.presenter.TrackInfoPresenter
import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack
import com.github.stakkato95.kmusic.mvp.view.TrackInfoView
import com.github.stakkato95.kmusic.util.extensions.blur
import com.github.stakkato95.kmusic.util.extensions.loadCover
import com.github.stakkato95.kmusic.util.extensions.picasso
import com.github.stakkato95.kmusic.util.picasso.BlurTransformation
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.fragment_track_info.*
import javax.inject.Inject

/**
 * Created by artsiomkaliaha on 14.07.17.
 */
class TrackInfoFragment : BaseFragment(), TrackInfoView {

    @Inject
    lateinit var presenter: TrackInfoPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_track_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val blurredImage = (coverImageView.drawable as BitmapDrawable).bitmap.blur(context)
        coverImageView.setImageBitmap(blurredImage)
    }

    override fun injectPresenter(): LifecycleObserver {
        App.INJECTOR.plusTrackInfoComponent(this).inject(this)
        return presenter
    }

    override fun showTrackInfo(playerTrack: PlayerTrack) {
        artistView.text = playerTrack.author
        context.picasso
                .loadCover(playerTrack.coverPath)
                .transform(BlurTransformation(context))
                .error(R.drawable.test_background)
                .into(coverImageView, object : Callback {
                    override fun onSuccess() {}

                    override fun onError() {
                        val bitmap = (coverImageView.drawable as BitmapDrawable)
                                .bitmap
                                .blur(this@TrackInfoFragment.context)
                        coverImageView.setImageBitmap(bitmap)
                    }
                })
    }
}