package com.dev;

import android.Manifest;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;

import dev.utils.app.SDCardUtils;
import dev.utils.app.player.DevMediaManager;
import dev.utils.app.player.DevVideoPlayerControl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 申请权限
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M) {
            requestPermissions(new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        // 多媒体相关
        media();
    }

    /**
     * 多媒体相关操作
     */
    private void media(){
//        // 默认事件监听
//        DevMediaManager.getInstance().setMeidaListener(new DevMediaManager.MediaListener() {
//            @Override
//            public void onPrepared() {
//                DevMediaManager.getInstance().getMediaPlayer().start();
//            }
//
//            @Override
//            public void onCompletion() {
//            }
//
//            @Override
//            public void onBufferingUpdate(int percent) {
//            }
//
//            @Override
//            public void onSeekComplete() {
//            }
//
//            @Override
//            public void onError(int what, int extra) {
//            }
//
//            @Override
//            public void onVideoSizeChanged(int width, int height) {
//            }
//        });

//        // 播放音频
//        DevMediaManager.getInstance().playPrepareRaw(R.raw.dev_beep);
//        DevMediaManager.getInstance().playPrepareAssets("a.mp3");
//        DevMediaManager.getInstance().playPrepare(SDCardUtils.getSDCardPath() + "/a.mp3");
//        DevMediaManager.getInstance().playPrepare("http://xxx.mp3");
//        DevMediaManager.getInstance().playPrepare(new DevMediaManager.MediaSet(){}); // 自由设置信息

//        // 播放视频
//        DevVideoPlayerControl control = new DevVideoPlayerControl((SurfaceView) findViewById(R.id.surface));
//        control.startPlayer(SDCardUtils.getSDCardPath() + "/video_3.mp4");
//        control.startPlayer("http://xxx.mp4");
//        control.startPlayer(new DevMediaManager.MediaSet(){}); // 自由设置信息
    }
}
