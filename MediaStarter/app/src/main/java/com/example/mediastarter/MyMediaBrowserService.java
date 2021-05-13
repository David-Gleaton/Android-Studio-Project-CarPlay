package com.example.mediastarter;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;

import java.util.ArrayList;
import java.util.List;

import static android.media.session.PlaybackState.PLAYBACK_POSITION_UNKNOWN;

public class MyMediaBrowserService extends MediaBrowserServiceCompat {
    public MyMediaBrowserService() {
    }

    private static final String MEDIA_ROOT_ID = "media_root_id";
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaSession = new MediaSessionCompat(this, "MediaSessionTag");
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE)
                .setState(PlaybackStateCompat.STATE_PLAYING, 0, 1);
        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(callback);
        setSessionToken(mediaSession.getSessionToken());
    }


    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return new MediaBrowserServiceCompat.BrowserRoot(MEDIA_ROOT_ID, null);
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

        List<MediaBrowserCompat.MediaItem> mediaItems = new ArrayList<>();

        if (parentId.equals(MEDIA_ROOT_ID)) {

            //Build Top Level Folder
            MediaDescriptionCompat.Builder builder = new MediaDescriptionCompat.Builder();
            builder.setDescription("A Top Level Folder");
            builder.setTitle("Songs in Hierarchy");
            builder.setMediaId("Folder0");
            MediaDescriptionCompat folderbuilt = builder.build();
            MediaBrowserCompat.MediaItem folder0 = new MediaBrowserCompat.MediaItem(folderbuilt, 0);

            //Add Folder to list
            mediaItems.add(folder0);

            //Build Top Level Song
            builder = new MediaDescriptionCompat.Builder();
            builder.setDescription("A Top Level Song");
            builder.setTitle("Lonely at the Top");
            builder.setMediaId("Song0");
            MediaDescriptionCompat songbuilt = builder.build();
            MediaBrowserCompat.MediaItem song0 = new MediaBrowserCompat.MediaItem(songbuilt, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE);

            //Add Song to list
            mediaItems.add(song0);


        } else if (parentId.equals("Folder0")) {
            //Build 1st Level Folder
            MediaDescriptionCompat.Builder builder = new MediaDescriptionCompat.Builder();
            builder.setDescription("A 1st Level Folder");
            builder.setTitle("Songs Below the Top");
            builder.setMediaId("Folder1");
            MediaDescriptionCompat folderbuilt = builder.build();
            MediaBrowserCompat.MediaItem folder1 = new MediaBrowserCompat.MediaItem(folderbuilt, 0);

            //Add Folder to list
            mediaItems.add(folder1);

            //Build 1st Level Song
            builder = new MediaDescriptionCompat.Builder();
            builder.setDescription("A 1st Level Song");
            builder.setTitle("Eye of the Tiger");
            builder.setMediaId("Song1");
            MediaDescriptionCompat songbuilt = builder.build();
            MediaBrowserCompat.MediaItem song1 = new MediaBrowserCompat.MediaItem(songbuilt, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE);

            //Add Song to list
            mediaItems.add(song1);


        } else if(parentId.equals("Folder1")){

            //Build 1st 2nd Level Song
            MediaDescriptionCompat.Builder builder = new MediaDescriptionCompat.Builder();
            builder.setDescription("A 2nd Level Song");
            builder.setTitle("Hayloft");
            builder.setMediaId("Song2");
            MediaDescriptionCompat songbuilt = builder.build();
            MediaBrowserCompat.MediaItem song2 = new MediaBrowserCompat.MediaItem(songbuilt, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE);

            //Add Song to list
            mediaItems.add(song2);

            //Build 2st 2nd Level Song
            builder = new MediaDescriptionCompat.Builder();
            builder.setDescription("A 2nd Level Song");
            builder.setTitle("I am Steve");
            builder.setMediaId("Song3");
            songbuilt = builder.build();
            MediaBrowserCompat.MediaItem song3 = new MediaBrowserCompat.MediaItem(songbuilt, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE);

            //Add Song to list
            mediaItems.add(song3);




        }
        result.sendResult(mediaItems);
    }

    private MediaSessionCompat.Callback callback = new
            MediaSessionCompat.Callback() {
                @Override
                public void onPause() {
                    super.onPause();
                    PlaybackStateCompat playbackState = new PlaybackStateCompat.Builder().setState(PlaybackStateCompat.STATE_PAUSED, PLAYBACK_POSITION_UNKNOWN, 1).build();
                            mediaSession.setPlaybackState(playbackState);
                }
                @Override
                public void onPlay() {
                    super.onPlay();
                    PlaybackStateCompat playbackState = new PlaybackStateCompat.Builder().setState(PlaybackStateCompat.STATE_PLAYING, PLAYBACK_POSITION_UNKNOWN, 1).build();
                            mediaSession.setPlaybackState(playbackState);
                }

            };



}