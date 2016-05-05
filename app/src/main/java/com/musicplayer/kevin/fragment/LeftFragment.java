package com.musicplayer.kevin.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.musicplayer.kevin.base.impl.ChatPager;
import com.musicplayer.kevin.bean.ChatData;
import com.musicplayer.kevin.bean.ChatData.ChatMenuData;
import com.musicplayer.kevin.musiciplayer.R;
import com.musicplayer.kevin.musiciplayer.activity.MainActivity;
import com.musicplayer.kevin.musicplayer.global.GlobalContents;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/4/24.
 */
public class LeftFragment extends BaseFragment {

    private ListView lvList;
    private int mCurrentPosition;
    private ArrayList<ChatMenuData> myMenuList;
    private MenuAdapter menuAdapter;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_left, null);
        lvList = (ListView) view.findViewById(R.id.lv_list);
        return view;
    }

    @Override
    public void initData() {

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mCurrentPosition = position;
                //  Log.i(GlobalContents.TAG, "currentposition: " + mCurrentPosition);
                menuAdapter.notifyDataSetChanged();
                setCurrentMenuDetailPager(position);
                toggleSlidingMenu();
            }
        });

    }

    private void toggleSlidingMenu() {

        MainActivity mainUi = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        slidingMenu.toggle();
    }

    private void setCurrentMenuDetailPager(int position) {

        MainActivity mainUi = (MainActivity) mActivity;
        ContentFragment fragment = mainUi.getContentFragment();
        ChatPager chatPager = fragment.getChatPager();
        chatPager.setCurrentMenuDetailPager(position);

    }

    public void setMenuData(ChatData chatData) {

        myMenuList = chatData.data;
        menuAdapter = new MenuAdapter();
        lvList.setAdapter(menuAdapter);

    }

    class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myMenuList.size();
        }

        @Override
        public ChatMenuData getItem(int position) {
            return myMenuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.list_menu_item, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            ChatMenuData chatMenuData = getItem(position);
            tvTitle.setText(chatMenuData.title);

            if (mCurrentPosition == position) {
                tvTitle.setEnabled(true);
               // Log.i(GlobalContents.TAG, "currentposition: " + mCurrentPosition + " " + position);
            } else {
                tvTitle.setEnabled(false);
             //   Log.i(GlobalContents.TAG, "currentposition: " + position);
            }

            return view;
        }
    }
}
