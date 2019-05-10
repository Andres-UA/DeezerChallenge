package com.example.deezerchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.deezerchallenge.adapter.PlayListAdapter;
import com.example.deezerchallenge.model.SearchPlaylist;
import com.example.deezerchallenge.service.ServiceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText textSearch;
    private ImageView btnSearch;

    private RecyclerView rvPlaylist;
    private PlayListAdapter playlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSearch = findViewById(R.id.et_search);
        btnSearch = findViewById(R.id.btn_search);

        rvPlaylist = findViewById(R.id.rv_playlist);
        playlistAdapter = new PlayListAdapter();

        rvPlaylist.setLayoutManager(new LinearLayoutManager(this));
        rvPlaylist.setAdapter(playlistAdapter);
        rvPlaylist.setHasFixedSize(true);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = textSearch.getText().toString();
                if (!search.isEmpty()) {
                    new Thread(() -> {
                        new ServiceManager.GETSearchPlaylist(search, new ServiceManager.GETSearchPlaylist.OnResponseListener() {
                            @Override
                            public void onResponse(String response) {
                                runOnUiThread(() -> {

                                    JSONObject json = null;    // create JSON obj from string
                                    try {
                                        json = new JSONObject(response);
                                        JSONArray data = json.getJSONArray("data");

                                        String playlist_json = data.toString();

                                        ArrayList<SearchPlaylist> playlist = new Gson().fromJson(playlist_json, new TypeToken<List<SearchPlaylist>>() {
                                        }.getType());

                                        playlistAdapter.setData(playlist);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                });
                            }
                        });
                    }).start();
                }
            }
        });


    }
}
