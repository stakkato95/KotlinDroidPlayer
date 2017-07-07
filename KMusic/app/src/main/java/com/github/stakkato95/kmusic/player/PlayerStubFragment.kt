package com.github.stakkato95.kmusic.player

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.stakkato95.kmusic.R

/**
 * Created by artsiomkaliaha on 06.07.17.
 */
class PlayerStubFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fragment_player_stub, container, false)
}