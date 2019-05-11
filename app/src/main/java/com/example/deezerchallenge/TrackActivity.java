package com.example.deezerchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.deezerchallenge.model.Playlist;
import com.example.deezerchallenge.model.Track;
import com.example.deezerchallenge.service.ServiceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrackActivity extends AppCompatActivity {

    private ImageView ivCover;
    private TextView etTitle;
    private TextView etAuthor;
    private TextView etAlbum;
    private TextView etDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        ivCover = findViewById(R.id.track_image);
        etTitle = findViewById(R.id.track_title);
        etAuthor = findViewById(R.id.track_author);
        etAlbum = findViewById(R.id.track_album);
        etDuration = findViewById(R.id.track_duration);

        if (getIntent().hasExtra("id")) {
            String id = getIntent().getStringExtra("id");
            new Thread(() -> {
                new ServiceManager.GETTrack(id, new ServiceManager.GETTrack.OnResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        runOnUiThread(() -> {

                            JSONObject json = null;    // create JSON obj from string
                            try {
                                json = new JSONObject(response);


                                String track_json = json.toString();

                                Track track = new Gson().fromJson(track_json, new TypeToken<Track>() {
                                }.getType());

                                etTitle.setText(track.getTitle());
                                etAlbum.setText(track.getAlbum().getTitle());
                                etAuthor.setText(track.getArtist().getName());
                                etDuration.setText(track.getDuration() + "");

                                Glide.with(TrackActivity.this).load(track.getAlbum().getCover_medium()).into(ivCover);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
            }).start();
        }

    }
}
