package dev.utils.app;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 音频管理工具类
 * @author Ttt
 * <pre>
 *     AudioManager 的作用: 调整音量和控制响铃模式
 *     @see <a href="https://blog.csdn.net/feiduclear_up/article/details/78383451"/>
 *     <p></p>
 *     声音分类
 *     STREAM_VOICE_CALL: 通话声音
 *     STREAM_SYSTEM: 系统声音, 包括按键声音等
 *     STREAM_RING: 来电响铃
 *     STREAM_MUSIC: 媒体声音 ( 包括音乐、视频、游戏声音 )
 *     STREAM_ALARM: 闹钟声音
 *     STREAM_NOTIFICATION: 通知声音
 *     <p></p>
 *     声音模式分类
 *     RINGER_MODE_NORMAL: 正常模式
 *     所有声音都正常, 包括系统声音、来电响铃、媒体声音、闹钟、通知声音都有
 *     RINGER_MODE_SILENT: 静音模式
 *     该模式下, 来电响铃、通知、系统声音和震动都没有, 闹钟、通话声音保持, 大部分手机媒体声音依然有
 *     但是小米和少部分 oppo 手机在设置静音的同时会将媒体声音自动调整为 0, 此时没有媒体声音
 *     RINGER_MODE_VIBRATE: 震动模式
 *     该模式下, 来电、通知保持震动没有声音, 但是媒体、闹钟依然有声音, 不过小米手机只有正常模式和静音模式, 没有震动模式
 *     <p></p>
 *     所需权限:
 *     <uses-permission android:name="android.permission.ACCESS_NOTIFICATION_POLICY" />
 * </pre>
 */
public final class AudioManagerUtils {

    private AudioManagerUtils() {
    }

    // 日志 TAG
    private static final String TAG = AudioManagerUtils.class.getSimpleName();

    /**
     * 获取 AudioManager
     * @return {@link AudioManager}
     */
    public static AudioManager getAudioManager() {
        try {
            return (AudioManager) DevUtils.getContext().getSystemService(Context.AUDIO_SERVICE);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAudioManager");
        }
        return null;
    }

    // =

    /**
     * 获取指定声音流最大音量大小
     * @param streamType 流类型
     * @return 最大音量大小
     */
    public static int getStreamMaxVolume(final int streamType) {
        AudioManager audioManager = getAudioManager();
        if (audioManager != null) {
            try {
                return audioManager.getStreamMaxVolume(streamType);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getStreamMaxVolume");
            }
        }
        return 0;
    }

    /**
     * 获取指定声音流音量大小
     * @param streamType 流类型
     * @return 音量大小
     */
    public static int getStreamVolume(final int streamType) {
        AudioManager audioManager = getAudioManager();
        if (audioManager != null) {
            try {
                return audioManager.getStreamVolume(streamType);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getStreamVolume");
            }
        }
        return 0;
    }

    /**
     * 控制手机音量调小一个单位
     */
    public static void adjustVolumeLower() {
        adjustVolume(AudioManager.ADJUST_LOWER);
    }

    /**
     * 控制手机音量调大一个单位
     */
    public static void adjustVolumeRaise() {
        adjustVolume(AudioManager.ADJUST_RAISE);
    }

    /**
     * 控制手机音量, 调大或者调小一个单位
     * <pre>
     *     AudioManager.ADJUST_LOWER 可调小一个单位
     *     AudioManager.ADJUST_RAISE 可调大一个单位
     * </pre>
     * @param direction 音量方向 ( 调大、调小 )
     */
    public static void adjustVolume(final int direction) {
        AudioManager audioManager = getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.adjustVolume(direction, 0);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "adjustVolume");
            }
        }
    }

    // =

    /**
     * 设置媒体声音静音状态
     * @param state {@code true} 静音, {@code false} 非静音
     */
    public static void setStreamMuteByMusic(final boolean state) {
        setStreamMute(AudioManager.STREAM_MUSIC, state);
    }

    /**
     * 设置通话声音静音状态
     * @param state {@code true} 静音, {@code false} 非静音
     */
    public static void setStreamMuteByVoiceCall(final boolean state) {
        setStreamMute(AudioManager.STREAM_VOICE_CALL, state);
    }

    /**
     * 设置系统声音静音状态
     * @param state {@code true} 静音, {@code false} 非静音
     */
    public static void setStreamMuteBySystem(final boolean state) {
        setStreamMute(AudioManager.STREAM_SYSTEM, state);
    }

    /**
     * 设置来电响铃静音状态
     * @param state {@code true} 静音, {@code false} 非静音
     */
    public static void setStreamMuteByRing(final boolean state) {
        setStreamMute(AudioManager.STREAM_RING, state);
    }

    /**
     * 设置闹钟声音静音状态
     * @param state {@code true} 静音, {@code false} 非静音
     */
    public static void setStreamMuteByAlarm(final boolean state) {
        setStreamMute(AudioManager.STREAM_ALARM, state);
    }

    /**
     * 设置通知声音静音状态
     * @param state {@code true} 静音, {@code false} 非静音
     */
    public static void setStreamMuteByNotification(final boolean state) {
        setStreamMute(AudioManager.STREAM_NOTIFICATION, state);
    }

    /**
     * 设置指定声音流静音状态
     * @param streamType 流类型
     * @param state      {@code true} 静音, {@code false} 非静音
     */
    public static void setStreamMute(final int streamType, final boolean state) {
        AudioManager audioManager = getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.setStreamMute(streamType, state);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setStreamMute");
            }
        }
    }

    // =

    /**
     * 设置静音模式 ( 静音, 且无振动 )
     */
    public static void ringerSilent() {
        AudioManager audioManager = getAudioManager();
        if (audioManager != null) {
            try {
                if (isDoNotDisturb(true)) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "ringerSilent");
            }
        }
    }

    /**
     * 设置静音模式 ( 静音, 但有振动 )
     */
    public static void ringerVibrate() {
        AudioManager audioManager = getAudioManager();
        if (audioManager != null) {
            try {
                if (isDoNotDisturb(true)) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "ringerVibrate");
            }
        }
    }

    /**
     * 设置正常模式 ( 正常声音, 振动开关由 setVibrateSetting 决定 )
     */
    public static void ringerNormal() {
        AudioManager audioManager = getAudioManager();
        if (audioManager != null) {
            try {
                if (isDoNotDisturb(true)) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "ringerNormal");
            }
        }
    }

    // =

    /**
     * 判断是否授权 Do not disturb 权限
     * <pre>
     *     存在 Do not disturb 权限, 才可进行音量操作
     * </pre>
     * @param setting 如果不存在, 是否跳转到设置页面
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDoNotDisturb(final boolean setting) {
        try {
            NotificationManager notificationManager =
                    (NotificationManager) DevUtils.getContext().getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                    && !notificationManager.isNotificationPolicyAccessGranted()) {
                if (setting) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    DevUtils.getContext().startActivity(intent);
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isDoNotDisturb");
        }
        return false;
    }
}