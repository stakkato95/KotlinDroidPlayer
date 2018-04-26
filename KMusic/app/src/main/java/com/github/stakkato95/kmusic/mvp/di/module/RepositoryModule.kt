package com.github.stakkato95.kmusic.mvp.di.module

import android.content.Context
import com.github.stakkato95.kmusic.mvp.di.scope.ApplicationScope
import com.github.stakkato95.kmusic.mvp.repository.Repository
import com.github.stakkato95.kmusic.mvp.repository.RepositoryImpl
import com.github.stakkato95.kmusic.mvp.repository.database.DatabaseRepository
import com.github.stakkato95.kmusic.mvp.repository.database.DatabaseRepositoryImpl
import com.github.stakkato95.kmusic.mvp.repository.media.AlbumsCursorParser
import com.github.stakkato95.kmusic.mvp.repository.media.MediaCursorParser
import com.github.stakkato95.kmusic.mvp.repository.media.MediaStoreRepository
import com.github.stakkato95.kmusic.mvp.repository.media.MediaStoreRepositoryImpl
import com.github.stakkato95.kmusic.mvp.repository.room.KMusicDatabase
import dagger.Module
import dagger.Provides

/**
 * Created by artsiomkaliaha on 04.10.17.
 */
@Module
class RepositoryModule {

    @Provides
    @ApplicationScope
    fun provideRepository(media: MediaStoreRepository, database: DatabaseRepository): Repository
            = RepositoryImpl(media, database)

    //TODO use annotiations to distinguisch media parser and albumsParser
    @Provides
    @ApplicationScope
    fun provideMediaSoreRepository(context: Context, mediaParser: MediaCursorParser, albumsParser: AlbumsCursorParser): MediaStoreRepository
            = MediaStoreRepositoryImpl(context, mediaParser, albumsParser)

    @Provides
    @ApplicationScope
    fun provideMediaParser() = MediaCursorParser()

    @Provides
    @ApplicationScope
    fun provideAlbumsParser() = AlbumsCursorParser()

    @Provides
    @ApplicationScope
    fun provideDatabaseRepository(database: KMusicDatabase): DatabaseRepository = DatabaseRepositoryImpl(database)
}