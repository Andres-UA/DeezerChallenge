package com.example.deezerchallenge.service;

import android.util.Log;

import com.example.deezerchallenge.util.HTTPSWebUtil;

import java.io.IOException;

public class ServiceManager {

    public static final String SEARCH_PLAYLIST_URL = "https://api.deezer.com/search/playlist?q=";
    public static final String PLAYLIST_URL = "https://api.deezer.com/playlist/";
    public static final String TRACK_URL = "https://api.deezer.com/track/";


    public static class SimpleGET {
        OnResponseListener listener;

        public SimpleGET(OnResponseListener listener) {
            this.listener = listener;
            HTTPSWebUtil util = new HTTPSWebUtil();
            try {
                String response = util.GETrequest(PLAYLIST_URL);
                listener.onResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public interface OnResponseListener {
            void onResponse(String response);
        }
    }

    public static class GETSearchPlaylist {
        OnResponseListener listener;

        public GETSearchPlaylist(String search, OnResponseListener listener) {
            this.listener = listener;
            HTTPSWebUtil util = new HTTPSWebUtil();
            try {
                String response = util.GETrequest(SEARCH_PLAYLIST_URL + search);
                listener.onResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public interface OnResponseListener {
            void onResponse(String response);
        }
    }

    public static class GETPlaylist {
        OnResponseListener listener;

        public GETPlaylist(String id, OnResponseListener listener) {
            this.listener = listener;
            HTTPSWebUtil util = new HTTPSWebUtil();
            try {
                String response = util.GETrequest(PLAYLIST_URL + id);
                listener.onResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public interface OnResponseListener {
            void onResponse(String response);
        }
    }

    public static class GETTrack {
        OnResponseListener listener;

        public GETTrack(String id, OnResponseListener listener) {
            this.listener = listener;
            HTTPSWebUtil util = new HTTPSWebUtil();
            try {
                String response = util.GETrequest(TRACK_URL + id);
                listener.onResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public interface OnResponseListener {
            void onResponse(String response);
        }
    }

}
