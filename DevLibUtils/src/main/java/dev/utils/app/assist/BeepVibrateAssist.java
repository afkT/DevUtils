package dev.utils.app.assist;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.annotation.RawRes;
import android.support.annotation.RequiresPermission;

import java.io.Closeable;

import dev.utils.LogPrintUtils;

/**
 * detail: 播放「bee」的声音, 并且震动辅助类
 * @author Ttt
 * <pre>
 *     所需权限:
 *     <uses-permission android:name="android.permission.VIBRATE"/>
 * </pre>
 */
public final class BeepVibrateAssist implements Closeable {

    // 日志 TAG
    private static final String TAG = BeepVibrateAssist.class.getSimpleName();
    // Activity
    private final Activity mActivity;
    // 播放资源对象
    private MediaPlayer mMediaPlayer = null;
    // 是否需要震动
    private boolean mIsVibrate = true;
    // 震动时间
    private long mVibrateDuration = 200L;

    /**
     * 构造函数
     * @param activity {@link Activity}
     */
    public BeepVibrateAssist(final Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 构造函数
     * @param activity {@link Activity}
     * @param rawId    R.raw.id
     */
    public BeepVibrateAssist(final Activity activity, @RawRes final int rawId) {
        this.mActivity = activity;
        this.mMediaPlayer = buildMediaPlayer(activity, rawId);
    }

    /**
     * 构造函数
     * @param activity {@link Activity}
     * @param filePath 文件路径
     */
    public BeepVibrateAssist(final Activity activity, final String filePath) {
        this.mActivity = activity;
        this.mMediaPlayer = buildMediaPlayer(filePath);
    }

    // ================
    // = 内部判断方法 =
    // ================

    /**
     * 判断是否允许播放声音
     * @return {@code true} 允许, {@code false} 不允许
     */
    private boolean shouldBeep() {
        try {
            // RINGER_MODE_NORMAL( 普通 )、RINGER_MODE_SILENT( 静音 )、RINGER_MODE_VIBRATE( 震动 )
            AudioManager audioService = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);
            if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
                return false; // 只有属于, 静音、震动, 才不播放
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "shouldBeep");
        }
        return true;
    }

    /**
     * 更新播放流处理
     */
    private synchronized void streamUpdate() {
        if (shouldBeep() && mMediaPlayer != null) {
            try {
                // The volume on STREAM_SYSTEM is not adjustable, and users found it too loud,
                // so we now play on the music stream.
                mActivity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "update");
            }
        }
    }

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 判断是否允许播放声音
     * @return {@code true} 允许, {@code false} 不允许
     */
    public boolean isPlayBeep() {
        return shouldBeep();
    }

    /**
     * 判断是否允许震动
     * @return {@code true} 允许, {@code false} 不允许
     */
    public boolean isVibrate() {
        return mIsVibrate;
    }

    /**
     * 设置是否允许震动
     * @param vibrate 是否允许震动
     * @return {@link BeepVibrateAssist}
     */
    public BeepVibrateAssist setVibrate(final boolean vibrate) {
        setVibrate(vibrate, 200L);
        return this;
    }

    /**
     * 设置是否允许震动
     * @param vibrate         是否允许震动
     * @param vibrateDuration 震动时间 ( 毫秒 )
     * @return {@link BeepVibrateAssist}
     */
    public BeepVibrateAssist setVibrate(final boolean vibrate, final long vibrateDuration) {
        this.mIsVibrate = vibrate;
        this.mVibrateDuration = vibrateDuration;
        return this;
    }

    /**
     * 设置播放资源对象
     * @param mediaPlayer {@link MediaPlayer}
     * @return {@link BeepVibrateAssist}
     */
    public BeepVibrateAssist setMediaPlayer(final MediaPlayer mediaPlayer) {
        this.mMediaPlayer = mediaPlayer;
        // 更新播放流处理
        streamUpdate();
        return this;
    }

    /**
     * 进行播放声音, 并且震动
     */
    @RequiresPermission(android.Manifest.permission.VIBRATE)
    public synchronized void playBeepSoundAndVibrate() {
        // 判断是否允许播放
        if (shouldBeep() && mMediaPlayer != null) {
            try {
                // 播放
                mMediaPlayer.start();
            } catch (Exception e) {
            }
        }
        // 判断是否允许震动
        if (mIsVibrate) {
            try {
                Vibrator vibrator = (Vibrator) mActivity.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(mVibrateDuration);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 关闭震动、提示声, 并释放资源
     */
    @Override
    public synchronized void close() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    // =========================
    // = 创建 MediaPlayer 处理 =
    // =========================

    /**
     * 创建 MediaPlayer 对象
     * @param context {@link Context}
     * @param rawId   R.raw.id
     * @return {@link MediaPlayer}
     */
    public static MediaPlayer buildMediaPlayer(final Context context, @RawRes final int rawId) {
        return buildMediaPlayer(context, rawId, 0.1f);
    }

    /**
     * 创建 MediaPlayer 对象
     * @param context    {@link Context}
     * @param rawId      R.raw.id
     * @param beepVolume 音量
     * @return {@link MediaPlayer}
     */
    public static MediaPlayer buildMediaPlayer(final Context context, @RawRes final int rawId, final float beepVolume) {
        final MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                LogPrintUtils.dTag(TAG, "buildMediaPlayer - onCompletion");
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public synchronized boolean onError(MediaPlayer mp, int what, int extra) {
                LogPrintUtils.dTag(TAG, "buildMediaPlayer - onError => what: " + what + ", extra: " + extra);
                // 播放异常, 直接不处理
                return true;
            }
        });
        try {
            AssetFileDescriptor file = context.getResources().openRawResourceFd(rawId);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
            } finally {
                try {
                    file.close();
                } catch (Exception e) {
                }
            }
            mediaPlayer.setVolume(beepVolume, beepVolume);
            mediaPlayer.prepare();
            return mediaPlayer;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "buildMediaPlayer");
            mediaPlayer.release();
            return null;
        }
    }

    // =

    /**
     * 创建 MediaPlayer 对象
     * @param filePath 文件路径
     * @return {@link MediaPlayer}
     */
    public static MediaPlayer buildMediaPlayer(final String filePath) {
        return buildMediaPlayer(filePath, 0.1f);
    }

    /**
     * 创建 MediaPlayer 对象
     * @param filePath   文件路径
     * @param beepVolume 音量
     * @return {@link MediaPlayer}
     */
    public static MediaPlayer buildMediaPlayer(final String filePath, final float beepVolume) {
        final MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                LogPrintUtils.dTag(TAG, "buildMediaPlayer - onCompletion");
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public synchronized boolean onError(MediaPlayer mp, int what, int extra) {
                LogPrintUtils.dTag(TAG, "buildMediaPlayer - onError => what: " + what + ", extra: " + extra);
                // 播放异常, 直接不处理
                return true;
            }
        });
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.setVolume(beepVolume, beepVolume);
            mediaPlayer.prepare();
            return mediaPlayer;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "buildMediaPlayer");
            mediaPlayer.release();
            return null;
        }
    }
}