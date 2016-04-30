package com.musicplayer.kevin.bean;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/4/29.
 */
public class MusicData {

    public int retcode;
    public ArrayList<MusicListData> music;

    public class MusicListData {

        public int id;
        public String name;
        public String url;


        @Override
        public String toString() {
            return "MusicListData{" + "url='" + url + '\'' +
                    ", name='" + name + '\'' + '}';
        }
    }
}
