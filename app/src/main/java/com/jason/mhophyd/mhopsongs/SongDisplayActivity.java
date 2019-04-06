package com.jason.mhophyd.mhopsongs;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toolbar;

public class SongDisplayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_display);


        displaySong();

    }



    private void displaySong() {
        Intent intent = getIntent();
        String Song = intent.getStringExtra("SONG_INFO");
        //Log.i("JasonMessage",Song);
        TextView songDisplay = findViewById(R.id.Song_Display_View);
        Spanned aboutText = Html.fromHtml("<h1>123</h1>"+Song);
//        songDisplay.setText(aboutText);
        WebView wb = findViewById(R.id.web1);
        wb.loadDataWithBaseURL(null, Song, "text/html", "utf-8", null);;


    }

}
