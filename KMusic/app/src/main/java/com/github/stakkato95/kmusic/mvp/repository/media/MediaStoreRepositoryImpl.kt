package com.github.stakkato95.kmusic.mvp.repository.media

import android.content.Context
import com.github.stakkato95.kmusic.mvp.repository.media.extractor.AlbumsExtractor
import com.github.stakkato95.kmusic.mvp.repository.media.extractor.TrackExtractor
import com.github.stakkato95.kmusic.mvp.repository.model.PlayerTrack
import io.reactivex.Observable

/**
 * Created by artsiomkaliaha on 06.10.17.
 */
class MediaStoreRepositoryImpl(val context: Context) : MediaStoreRepository {

    private val extractors = listOf(TrackExtractor(), AlbumsExtractor())

    override fun getAllTracks(): Observable<List<PlayerTrack>> {
        return Observable.create<List<PlayerTrack>> {
            val tracks = mutableListOf<PlayerTrack>()
            extractors.forEach { it.extract(context, tracks) }

            //TODO for 2nd release
//            tracks.forEach {
//                val extractor = MediaExtractor()
//                extractor.setDataSource(it.path)
//                val format = extractor.getTrackFormat(0)
//                try {
//                    it.bitRate = format.getInteger(MediaFormat.KEY_BIT_RATE) / 1000
//                    it.sampleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE) / 1000
//                } catch (e: Exception) {
//                    //ignored
//                }
//                extractor.release()
//            }

            it.onNext(tracks)
            it.onComplete()
        }
    }
}