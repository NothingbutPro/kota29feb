package com.ics.admin.Student_main_app.Student_UI;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.ics.admin.R;
import com.ics.admin.Student_main_app.LovelyProgressDialogs;

public class _Student_Video_Lecture_Activity extends AppCompatActivity {
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    String videoURL = "http://ihisaab.in/school_lms/uploads/video/";
    LovelyProgressDialogs lovelyProgressDialogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity___student__video__lecture_);
        exoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exo_player_view);
      videoURL = videoURL+getIntent().getStringExtra("vid_name");
        lovelyProgressDialogs = new LovelyProgressDialogs(this);
        lovelyProgressDialogs.StartDialog("Streaming Started");
//        videoURL = "http://ihisaab.in/school_lms/uploads/video/y2mate_com_-_the_witcher_final_trailer_netflix_eb90gqGYP9c_1080p.mp4";
        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            Uri videoURI = Uri.parse(videoURL);
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);
            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
            lovelyProgressDialogs.DismissDialog();
        }catch (Exception e){
            lovelyProgressDialogs.DismissDialog();
            Toast.makeText(this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            Log.e("MainAcvtivity"," exoplayer error "+ e.toString());
        }

    }

    @Override
    protected void onDestroy() {
        exoPlayer.stop();
        exoPlayer.release();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        exoPlayer.stop();
        exoPlayer.release();
        super.onBackPressed();
    }
}
