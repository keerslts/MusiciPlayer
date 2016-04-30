package com.musicplayer.kevin.utils;

import android.util.Log;

import com.musicplayer.kevin.musicplayer.global.GlobalContents;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 2016/4/28.
 */
public class LoadMusic {

    private static List<Map<String, Object>> musics = new ArrayList<>();
    private static int music_number = 0;
    private static int end_flag = 0;

    public static int getEnd_flag() {
        return end_flag;
    }

    public static void setEnd_flag(int end_flag) {
        LoadMusic.end_flag = end_flag;
    }

    public static List<Map<String, Object>> getMusics() {
        return musics;
    }

    public static int getMusic_number() {
        return music_number;
    }

    public static void initDate() {
        musics.clear();
        music_number = 0;
        end_flag = 0;
    }

    public static void loadMusicFile(File file) {

        File[] fs = file.listFiles();

        //file.getAbsolutePath();
        for (int i = 0; i < fs.length; i++) {
            File f = fs[i];
            Map<String, Object> map = new HashMap<>();
            if (f.isDirectory()) {
                loadMusicFile(f);
            } else {
                String flag = f.getName().substring(f.getName().lastIndexOf(".") + 1).toLowerCase();
                if (flag.equals("mp3")) {
                    map.put("fileName", f.getName());
                    map.put("filePath", f.getAbsolutePath());
                    //  map.put("flag", 1);
                    musics.add(map);
                    music_number++;
                    // Log.i(GlobalContents.TAG, "music: "+ f.getAbsolutePath());
                }
            }
        }
        // Log.i(GlobalContents.TAG, "load_music: "+ music_number+ "  "+ musics.size());

    }
}
