package com.github.stakkato95.kmusic.screen.trackinfo

import android.arch.lifecycle.LifecycleObserver
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.graphics.Palette
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

    interface TitleAware {

        fun setTitle(title: String)
    }

    @Inject
    lateinit var presenter: TrackInfoPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_track_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            val blurredImage = (coverImageView.drawable as BitmapDrawable).bitmap.blur(it)
            coverImageView.setImageBitmap(blurredImage)
        }
    }

    override fun injectPresenter(): LifecycleObserver {
        App.INJECTOR.plusTrackInfoComponent(this).inject(this)
        return presenter
    }

    override fun showTrackInfo(playerTrack: PlayerTrack) {
        findFirstResponder<TitleAware>()?.setTitle(playerTrack.name)

        val paletteBitmap = BitmapFactory.decodeFile("file://" + playerTrack.coverPath)
        val palette = Palette.Builder(paletteBitmap).generate()
        palette.mutedSwatch?.rgb?.let { artistView.setTextColor(it) }


        if (playerTrack.bitRate == PlayerTrack.SAMPLE_BIT_RATE_UNSET
                || playerTrack.sampleRate == PlayerTrack.SAMPLE_BIT_RATE_UNSET) {
            audioFormatView.visibility = View.GONE
            audioFormatLabelView.visibility = View.GONE
        } else {
            audioFormatView.visibility = View.VISIBLE
            audioFormatLabelView.visibility = View.VISIBLE
            audioFormatView.text = "MP3 ${playerTrack.sampleRate} KHz STEREO ${playerTrack.bitRate} Kb/s"
        }

        artistView.text = playerTrack.author
        context?.let {
            it.picasso
                    .loadCover(playerTrack.coverPath)
                    .transform(BlurTransformation(it))
                    .error(R.drawable.test_background)
                    .into(coverImageView, object : Callback {
                        override fun onSuccess() {}

                        override fun onError() {
                            val bitmap = (coverImageView.drawable as BitmapDrawable)
                                    .bitmap
                                    .blur(it)
                            coverImageView.setImageBitmap(bitmap)
                        }
                    })
        }
    }
}