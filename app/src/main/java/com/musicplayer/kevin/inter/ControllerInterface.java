package com.musicplayer.kevin.inter;

/**
 * Created by Kevin on 2016/4/26.
 */
public interface ControllerInterface {
    void play();
    void pause();
    void continuePlay();
    void seekTo(int progress);
}
