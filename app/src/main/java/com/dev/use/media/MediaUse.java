package com.dev.use.media;

import android.media.MediaPlayer;
import android.view.SurfaceView;

import com.dev.R;

import dev.utils.app.SDCardUtils;
import dev.utils.app.player.DevMediaManager;
import dev.utils.app.player.DevVideoPlayerControl;

/**
 * detail: 多媒体使用方法
 * Created by Ttt
 */
class MediaUse {

    /**
     * 多媒体使用方法
     */
    private void mediaUse(){
        // 默认事件监听
        DevMediaManager.getInstance().setMeidaListener(new DevMediaManager.MediaListener() {
            @Override
            public void onPrepared() {
                DevMediaManager.getInstance().getMediaPlayer().start();
            }

            @Override
            public void onCompletion() {
            }

            @Override
            public void onBufferingUpdate(int percent) {
            }

            @Override
            public void onSeekComplete() {
            }

            @Override
            public void onError(int what, int extra) {
            }

            @Override
            public void onVideoSizeChanged(int width, int height) {
            }
        });

        // =======

        // 播放音频
        DevMediaManager.getInstance().playPrepareRaw(R.raw.dev_beep);
        DevMediaManager.getInstance().playPrepareAssets("a.mp3");
        DevMediaManager.getInstance().playPrepare(SDCardUtils.getSDCardPath() + "/a.mp3");
        DevMediaManager.getInstance().playPrepare("http://xxx.mp3");
        DevMediaManager.getInstance().playPrepare(new DevMediaManager.MediaSet(){

            @Override
            public void setMediaConfig(MediaPlayer mediaPlayer) throws Exception {
                mediaPlayer.setDataSource("xxx");
            }
        }); // 自由设置信息

        // =======

        SurfaceView surfaceView = null;
        // 播放视频
        DevVideoPlayerControl control = new DevVideoPlayerControl(surfaceView);
        control.startPlayer(SDCardUtils.getSDCardPath() + "/video_3.mp4");
        control.startPlayer("http://xxx.mp4");
        control.startPlayer(new DevMediaManager.MediaSet(){
            @Override
            public void setMediaConfig(MediaPlayer mediaPlayer) throws Exception {
                mediaPlayer.setDataSource("xxx");
            }
        }); // 自由设置信息
    }
}
