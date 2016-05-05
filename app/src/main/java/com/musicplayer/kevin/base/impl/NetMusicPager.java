package com.musicplayer.kevin.base.impl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.musicplayer.kevin.base.BasePager;
import com.musicplayer.kevin.bean.MusicData;
import com.musicplayer.kevin.inter.ControllerInterface;
import com.musicplayer.kevin.musiciplayer.R;
import com.musicplayer.kevin.musicplayer.global.GlobalContents;
import com.musicplayer.kevin.service.MusicService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 2016/4/25.
 */
public class NetMusicPager extends BasePager {

    private MusicData musicData;
    private ListView lv_musics;
    private Button play;
    private Button next_music;
    private Button up_music;
    private int is_run;
    private String next_path;
    private String up_path;
    private int music_number = 0;
    private int music_position;


    private static SeekBar sk_bar;
    public ControllerInterface ci;
    public String path;

    public String getPath() {
        return path;
    }


    private ArrayList<MusicData.MusicListData> net_music_list;
    private ArrayList music_name_list = new ArrayList();
    private ArrayList music_path_list = new ArrayList();


    public static Handler handler = new Handler() {
        public void handleMessage(Message message) {
            Bundle bundle = message.getData();
            int duration = bundle.getInt("duration");
            int currentPosition = bundle.getInt("currentPosition");
            //  Log.i(GlobalContents.TAG, "duration: " + duration + "   " + currentPosition);
            sk_bar.setMax(duration);
            sk_bar.setProgress(currentPosition);
        }
    };


    public NetMusicPager(Activity myActivity) {
        super(myActivity);
    }

    @Override
    public void initData() {
        //清除之前的布局
        flContent.removeAllViews();

        //网络音乐的信息初始化
        music_name_list.clear();
        music_path_list.clear();
        music_number = 0;

        tvTitle.setText("网络音乐");
        setSlidingMenuEnable(false);

        getDataFromServer();

        // initView();


    }

    public void startService(View view) {
        sk_bar = (SeekBar) view.findViewById(R.id.music_seekBar);
        sk_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //获取sk_bar的当前进度，然后设置给音乐服务的播放进度
                ci.seekTo(seekBar.getProgress());
                //Log.i(GlobalContents.TAG, "progress: "+ seekBar.getProgress());
            }
        });

        Intent intent = new Intent(mActivity, MusicService.class);

        intent.putExtra("path", path);
        mActivity.startService(intent);


        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ci = (ControllerInterface) service;

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, mActivity.BIND_AUTO_CREATE);
    }

    private void initView() {
        View view = View.inflate(mActivity, R.layout.music_content, null);
        play = (Button) view.findViewById(R.id.play);
        next_music = (Button) view.findViewById(R.id.next_music);
        up_music = (Button) view.findViewById(R.id.up_music);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_run = new MusicService().getFlag_run();
                if (is_run == 0) {
                    ci.play();
                    play.setText("暂停");

                } else if (is_run % 2 == 0) {
                    ci.continuePlay();
                    play.setText("暂停");

                } else if (is_run % 2 == 1) {
                    ci.pause();
                    play.setText("继续");
                }

            }
        });

        next_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (music_position != music_number - 1) {
                    next_path = GlobalContents.SERVER_URL + "/musics" + music_path_list.get(music_position + 1).toString();
                    // Log.i(GlobalContents.TAG, "next " + next_path);
                    up_path = null;

                    MusicService.setChange(next_path);
                    music_position++;
                } else {
                    Toast.makeText(mActivity, "当前是最后一首歌", Toast.LENGTH_SHORT).show();
                }
            }
        });

        up_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (music_position != 0) {
                    up_path = GlobalContents.SERVER_URL + "/musics" + music_path_list.get(music_position - 1).toString();
                    next_music = null;
                    MusicService.setChange(up_path);
                    music_position--;
                } else {
                    Toast.makeText(mActivity, "当前是第一首歌", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lv_musics = (ListView) view.findViewById(R.id.lv_music_content);

        lv_musics.setAdapter(new MyAdapter(mActivity, music_name_list));

        flContent.addView(view);

        startService(view);

        lv_musics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                path = GlobalContents.SERVER_URL + "/musics" + music_path_list.get(position).toString();


                music_position = position;

                MusicService service = new MusicService();
                service.setChange(path);
                play.setText("暂停");
                // Log.i(GlobalContents.TAG, "diji" + path);

            }
        });
    }

    private void getDataFromServer() {

        HttpUtils httpUtils = new HttpUtils();
        Log.i(GlobalContents.TAG, "json getdata");
        httpUtils.send(HttpMethod.GET,
                GlobalContents.MUSIC_URL, new RequestCallBack<Object>() {
                    @Override
                    public void onSuccess(ResponseInfo<Object> responseInfo) {
                        Log.i(GlobalContents.TAG, "json getdata2");
                        String result = (String) responseInfo.result;
                        Log.i(GlobalContents.TAG, "json " + result);
                        parseData(result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i(GlobalContents.TAG, "网络异常 ");
                        Toast.makeText(mActivity, "请检查网络设置", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void parseData(String result) {
        Gson gson = new Gson();
        musicData = gson.fromJson(result, MusicData.class);

        net_music_list = new ArrayList<MusicData.MusicListData>();
        net_music_list = musicData.music;
        music_number = net_music_list.size();
        for (int i = 0; i < net_music_list.size(); i++) {
            //  Log.i(GlobalContents.TAG, "music_name_list "+net_music_list.get(i).name);
            music_name_list.add(net_music_list.get(i).name);
            music_path_list.add(net_music_list.get(i).url);
        }
        initView();
    }


    private class MyAdapter extends BaseAdapter {
        private Context ctx;
        private ArrayList music_name_list;

        public MyAdapter(Context ctx, ArrayList music_name_list) {
            this.ctx = ctx;
            this.music_name_list = music_name_list;
        }

        @Override
        public int getCount() {
            return music_name_list.size();
        }

        @Override
        public Object getItem(int position) {
            return music_name_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(ctx).inflate(R.layout.music_list, null);
                //设置ListView中每一行的宽度和高度
                // convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Globals.SCREEN_HEIGHT/8));
            }

            TextView musicName = (TextView) convertView.findViewById(R.id.music_name);
            // Map<String, Object> map = musics.get(position);
            musicName.setText(music_name_list.get(position).toString());

            return convertView;
        }
    }
}
