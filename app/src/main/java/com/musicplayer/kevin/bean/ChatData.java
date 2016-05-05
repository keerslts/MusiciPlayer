package com.musicplayer.kevin.bean;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/5/2.
 */
public class ChatData {

    public int retcode;
    public ArrayList<ChatMenuData> data;

    public class ChatMenuData {

        public String id;
        public String title;
        public int type;
        public ArrayList<ChatTabData> children;

        @Override
        public String toString() {
            return "ChatMenuData{" + "id='" + id + '\'' +
                    ", title='" + title + '\'' + ", children=" + children + '}';
        }
    }

    public class ChatTabData {
        public String id;
        public String title;
        public int type;
        public String url;

        @Override
        public String toString() {
            return "ChatTabData{" + "title='" + title + '\'' +
                    ", url='" + url + '\'' + ", id='" + id + '\'' + '}';
        }
    }

    @Override
    public String toString() {
        return "ChatData{" + "data=" + data + '}';
    }
}
