package com.musicplayer.kevin.base.impl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.musicplayer.kevin.base.BasePager;
import com.musicplayer.kevin.musiciplayer.R;
import com.musicplayer.kevin.musicplayer.global.GlobalContents;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Kevin on 2016/4/25.
 */
public class LocalMusicPager extends BasePager {

    private List<Map<String, Object>> musics;

    public LocalMusicPager(Activity myActivity) {
        super(myActivity);
    }
    private ListView lv_musics;

    @Override
    public void initData() {
        tvTitle.setText("本地音乐");
        setSlidingMenuEnable(true);

        musics = new ArrayList<>();
        initView();

        View view = View.inflate(mActivity, R.layout.music_content,null);
        lv_musics = (ListView) view.findViewById(R.id.lv_music_content);

        lv_musics.setAdapter(new MyAdapter(mActivity,musics));
//        TextView textView = new TextView(mActivity);
//        textView.setText("本地音乐11");
//        textView.setTextColor(Color.RED);
//        textView.setTextSize(25);
//        textView.setGravity(Gravity.CENTER);

        flContent.addView(view);


    }

    private void initView() {

        File file = Environment.getExternalStorageDirectory();

        loadMusicFile(file);
    }

    private void loadMusicFile(File file) {

        File[] fs = file.listFiles();
        //file.getAbsolutePath();
        for(int i=0 ; i<fs.length;i++){
            File f = fs[i];
          //  String fileName = f.getName();
            Map<String, Object> map = new HashMap<>();
            if(f.isDirectory()) {
                loadMusicFile(f);
            }else{
                String flag = f.getName().substring(f.getName().lastIndexOf(".")+1).toLowerCase();
                if(flag.equals("mp3")){
                    map.put("fileName",f.getName());
                    map.put("fullPath",f.getAbsolutePath());
                    map.put("flag",1);
                    musics.add(map);
                   // Log.i(GlobalContents.TAG, "music: "+ f.getName());
                }
            }
        }
    }





    private class MyAdapter extends BaseAdapter {
        private Context ctx;
        private List<Map<String, Object>> musics;

        public MyAdapter(Context ctx, List<Map<String, Object>> musics) {
            this.ctx = ctx;
            this.musics = musics;
        }

        @Override
        public int getCount() {
            return musics.size();
        }

        @Override
        public Object getItem(int position) {
            return musics.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null) {
                convertView = LayoutInflater.from(ctx).inflate(R.layout.music_list,null);
                //设置ListView中每一行的宽度和高度
               // convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Globals.SCREEN_HEIGHT/8));
            }

            TextView musicName = (TextView) convertView.findViewById(R.id.music_name);
            Map<String, Object> map = musics.get(position);
            musicName.setText(map.get("fileName").toString());

            return convertView;
        }
    }
}
