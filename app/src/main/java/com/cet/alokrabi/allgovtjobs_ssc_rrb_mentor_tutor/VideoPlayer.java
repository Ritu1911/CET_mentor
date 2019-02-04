package com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor.Common.Common;

public class VideoPlayer extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    YouTubeRecyclerViewFragment.LastItemReachedListener mListener;

    //String title = getIntent().getStringExtra("title");

    YouTubePlayer.OnInitializedListener onInitializedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        );

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);

        if (Common.isConnectedToInternet(getBaseContext())) {
            Bundle extra = this.getIntent().getExtras();
            if (extra != null) {

                final String video = extra.getString("VIDEO");

                // final int position = Integer.parseInt(video);
                final int position = extra.getInt("POS");




                onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                        youTubePlayer.loadVideo(video);

                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                };
                youTubePlayerView.initialize(ApiKey.YOUTUBE_API_KEY, onInitializedListener);

            } else {
                Toast.makeText(VideoPlayer.this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
                return;
            }


        }
    /*  */


    }
    @Override
    public void onConfigurationChanged (Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
    }
    @Override
    public void onResume(){

        super.onResume();

        try {
            Bundle extra = this.getIntent().getExtras();
            if (extra != null) {
                final String video = extra.getString("VIDEO");

                onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                        youTubePlayer.loadVideo(video);

                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                };
                youTubePlayerView.initialize(ApiKey.YOUTUBE_API_KEY,onInitializedListener);
            }



        } catch (Throwable ignored) {}
    }

}
