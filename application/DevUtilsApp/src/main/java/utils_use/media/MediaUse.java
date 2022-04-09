package utils_use.media;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.SurfaceView;

import afkt.project.R;
import dev.utils.app.PathUtils;
import dev.utils.app.player.DevMediaManager;
import dev.utils.app.player.DevVideoPlayerControl;

/**
 * detail: 多媒体使用方法
 * @author Ttt
 */
public final class MediaUse {

    private MediaUse() {
    }

    // 日志 TAG
    private static final String TAG = MediaUse.class.getSimpleName();

    /**
     * 多媒体使用方法
     */
    private void mediaUse() {
        // 设置 TAG, 打印日志使用
        DevMediaManager.getInstance().setTAG(TAG);
        // 设置音量
        DevMediaManager.getInstance().setVolume(50);
        // 设置流类型
        DevMediaManager.getInstance().setAudioStreamType(AudioManager.STREAM_MUSIC);

        // 获取播放音量
        DevMediaManager.getInstance().getVolume();
        // 获取当前播放的地址
        DevMediaManager.getInstance().getPlayUri();
        // 获取播放的资源 id
        DevMediaManager.getInstance().getPlayRawId();
        // 获取 当前播放时间
        DevMediaManager.getInstance().getCurrentPosition();
        // 获取资源总时间
        DevMediaManager.getInstance().getDuration();
        // 获取播放进度百分比
        DevMediaManager.getInstance().getPlayPercent();
        // 获取 MediaPlayer 对象
        DevMediaManager.getInstance().getMediaPlayer();

        // 获取播放的视频高度
        DevMediaManager.getInstance().getVideoHeight();
        // 获取播放的视频宽度
        DevMediaManager.getInstance().getVideoWidth();

        // 是否播放中
        DevMediaManager.getInstance().isPlaying();
        // 停止操作
        DevMediaManager.getInstance().stop();
        // 暂停操作
        DevMediaManager.getInstance().pause();

        // 设置事件监听
        DevMediaManager.getInstance().setMediaListener(new DevMediaManager.MediaListener() {
            @Override
            public void onPrepared() {
                if (DevMediaManager.getInstance().isNotNullMediaPlayer()) {
                    // 播放操作
                    DevMediaManager.getInstance().getMediaPlayer().start();
                }
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
            public boolean onError(
                    int what,
                    int extra
            ) {
                return false;
            }

            @Override
            public void onVideoSizeChanged(
                    int width,
                    int height
            ) {
            }
        });

        // =

        // 播放音频
        DevMediaManager.getInstance().playPrepareRaw(R.raw.dev_beep);
        DevMediaManager.getInstance().playPrepareAssets("a.mp3");
        DevMediaManager.getInstance().playPrepare(PathUtils.getSDCard().getSDCardPath() + "/a.mp3");
        DevMediaManager.getInstance().playPrepare("http://xxx.mp3");
        DevMediaManager.getInstance().playPrepare(new DevMediaManager.MediaSet() {
            @Override
            public void setMediaConfig(MediaPlayer mediaPlayer)
                    throws Exception {
                mediaPlayer.setDataSource("xxx");
            }
        }); // 自由设置信息

        // =

        SurfaceView surfaceView = null;
        // 播放视频
        DevVideoPlayerControl control = new DevVideoPlayerControl(surfaceView);
        control.startPlayer(PathUtils.getSDCard().getSDCardPath() + "/video_3.mp4");
        control.startPlayer("http://xxx.mp4");
        control.startPlayer(new DevMediaManager.MediaSet() {
            @Override
            public void setMediaConfig(MediaPlayer mediaPlayer)
                    throws Exception {
                mediaPlayer.setDataSource("xxx");
            }
        }); // 自由设置信息
    }
}