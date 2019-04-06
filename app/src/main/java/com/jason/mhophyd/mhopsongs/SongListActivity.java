package com.jason.mhophyd.mhopsongs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SongListActivity extends AppCompatActivity {

    private Songs sNew;
    private SongIndex sNewIndex;
    public static String SONG_INFO = "SONG_INFO";
    public static String SONG_INDEX = "SONG_INDEX";
    private List<Songs> sList;
    private List<SongIndex> sListIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        try {
            initializeSongsList(null);
            initializeIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeIndex() throws IOException {
        final ListView indexListView = findViewById(R.id.index_List);
        indexListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        List<SongIndex> songsIndex = getSongsListIndex();
        ArrayAdapter<SongIndex> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,songsIndex);
        indexListView.setAdapter(arrayAdapter);

        indexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    SongIndex songs = (SongIndex) indexListView.getItemAtPosition(i);
//                    indexListView.setSelection(i);
                    view.setSelected(true);

                    initializeSongsList(songs.getiName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Intent intent = new Intent(SongListActivity.this,SongDisplayActivity.class);
//                Songs songs = (Songs) indexListView.getItemAtPosition(i);
//                intent.putExtra(SongListActivity.SONG_INFO, songs.getsSong());
//                startActivity(intent);
            }
        });

    }

    private void initializeSongsList(final String sIndex) throws IOException {

       final ListView songsListView = findViewById(R.id.song_List);
        List<Songs> songs = getSongsList(sIndex);
        ArrayAdapter<Songs> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,songs);
        songsListView.setAdapter(arrayAdapter);

        songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(SongListActivity.this,SongDisplayActivity.class);
                Songs songs = (Songs) songsListView.getItemAtPosition(i);
                intent.putExtra(SongListActivity.SONG_INFO, songs.getsSong());
//                intent.putExtra(SongListActivity.SONG_INDEX, sIndex);
                if (!(songs.getsIndex().equalsIgnoreCase("No songs for this index"))) {
                    startActivity(intent);
                }
                else{
                Snackbar.make(view, "Nothing to display", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_song_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public List<Songs> parseSongs(String sIndex) throws IOException {
        try {
//            System.out.println("this is parse songs");
            InputStream is = getApplicationContext().getAssets().open("songsList.json");

            //InputStream is = new URL("https://s3.ap-south-1.amazonaws.com/mhopsongs/songs/songsList.json").openStream();

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            
           // System.out.println(json);

            try {
                JSONObject obj = new JSONObject(json);
                JSONArray m_jArry = obj.getJSONArray("songsList");
                ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> m_li;
                sList = new ArrayList<>();

                for (int i = 0; i < m_jArry.length(); i++) {
                    sNew = new Songs();
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                   //jo_inside.getString("type");
                    //Log.i("jason",jo_inside.getString("sName"));
                    sNew.setsName(jo_inside.getString("sName"));
                    sNew.setsNumber(jo_inside.getString("sNumber"));
                    sNew.setsIndex(jo_inside.getString("sIndex"));
                   sNew.setsSong(jo_inside.getString("song"));

                   if(sIndex !=null ){
//                       System.out.println("going into index filter");
//                       System.out.println("this is actual index"+ sNew.getsIndex());
//                       System.out.println(sIndex.equalsIgnoreCase(sNew.getsIndex()));

                      if(sIndex.equalsIgnoreCase(sNew.getsIndex())){

                           sList.add(sNew);
                       }
                   }
                   else {
                       sList.add(sNew);
                   }

                }
                    if(sList.size() == 0){
                        sNew.setsName("No songs for this index");
                        sNew.setsNumber("No songs for this index");
                        sNew.setsIndex("No songs for this index");
                        sNew.setsSong("No songs for this index");
                        sList.add(sNew);
                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }catch (IOException ex) {
            System.out.println("No Internet Connection");
            ex.printStackTrace();

        }
        return sList;
    }


    public List<SongIndex> parseSongsIndex() throws IOException {
        try {

            InputStream is = getApplicationContext().getAssets().open("songsIndex.json");

            //InputStream is = new URL("https://s3.ap-south-1.amazonaws.com/mhopsongs/songs/songsList.json").openStream();

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            // System.out.println(json);

            try {
                JSONObject obj = new JSONObject(json);
                JSONArray m_jArry = obj.getJSONArray("songsIndex");
                ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> m_li;
                sListIndex = new ArrayList<>();

                for (int i = 0; i < m_jArry.length(); i++) {
                    sNewIndex = new SongIndex();
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    //jo_inside.getString("type");
                    //Log.i("jason",jo_inside.getString("sName"));
                    sNewIndex.setiName(jo_inside.getString("iName"));
                    sNewIndex.setiNumber(jo_inside.getString("iNumber"));
                    sListIndex.add(sNewIndex);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }catch (IOException ex) {
            System.out.println("No Internet Connection");
            ex.printStackTrace();

        }
        return sListIndex;
    }

    public List<Songs> getSongsList(String sIndex) throws IOException {
        try {
//            System.out.println("this is get songs function");
            return parseSongs(sIndex);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    public List<SongIndex> getSongsListIndex() throws IOException {
        try {
            return parseSongsIndex();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
