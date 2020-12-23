package dev.utils.app;

import android.app.NotificationManager;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;

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
 *     所需权限
 *     <uses-permission android:name="android.permission.ACCESS_NOTIFICATION_POLICY" />
 * </pre>
 */
public final class AudioManagerUtils {

    private AudioManagerUtils() {
    }

    // 日志 TAG
    private static final String TAG = AudioManagerUtils.class.getSimpleName();

    // ===========
    // = 音量大小 =
    // ===========

    /**
     * 获取指定声音流最大音量大小
     * @param streamType 流类型
     * @return 最大音量大小
     */
    public static int getStreamMaxVolume(final int streamType) {
        AudioManager audioManager = AppUtils.getAudioManager();
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
        AudioManager audioManager = AppUtils.getAudioManager();
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
     * 设置指定声音流音量大小
     * @param streamType 流类型
     * @param index      音量大小
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStreamVolume(
            final int streamType,
            final int index
    ) {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.setStreamVolume(streamType, index, 0);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setStreamVolume");
            }
        }
        return false;
    }

    // =

    /**
     * 控制手机音量, 调小一个单位
     * @return {@code true} success, {@code false} fail
     */
    public static boolean adjustVolumeLower() {
        return adjustVolume(AudioManager.ADJUST_LOWER);
    }

    /**
     * 控制手机音量, 调大一个单位
     * @return {@code true} success, {@code false} fail
     */
    public static boolean adjustVolumeRaise() {
        return adjustVolume(AudioManager.ADJUST_RAISE);
    }

    /**
     * 控制手机音量, 调大或者调小一个单位
     * <pre>
     *     AudioManager.ADJUST_LOWER 可调小一个单位
     *     AudioManager.ADJUST_RAISE 可调大一个单位
     * </pre>
     * @param direction 音量方向 ( 调大、调小 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean adjustVolume(final int direction) {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.adjustVolume(direction, 0);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "adjustVolume");
            }
        }
        return false;
    }

    // =

    /**
     * 控制指定声音流音量, 调小一个单位
     * @param streamType 流类型
     * @return {@code true} success, {@code false} fail
     */
    public static boolean adjustStreamVolumeLower(final int streamType) {
        return adjustStreamVolume(streamType, AudioManager.ADJUST_LOWER);
    }

    /**
     * 控制指定声音流音量, 调大一个单位
     * @param streamType 流类型
     * @return {@code true} success, {@code false} fail
     */
    public static boolean adjustStreamVolumeRaise(final int streamType) {
        return adjustStreamVolume(streamType, AudioManager.ADJUST_RAISE);
    }

    /**
     * 控制指定声音流音量, 调大或者调小一个单位
     * <pre>
     *     AudioManager.ADJUST_LOWER 可调小一个单位
     *     AudioManager.ADJUST_RAISE 可调大一个单位
     * </pre>
     * @param streamType 流类型
     * @param direction  音量方向 ( 调大、调小 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean adjustStreamVolume(
            final int streamType,
            final int direction
    ) {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.adjustStreamVolume(streamType, direction, 0);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "adjustStreamVolume");
            }
        }
        return false;
    }

    // ===========
    // = 静音状态 =
    // ===========

    /**
     * 设置媒体声音静音状态
     * @param state {@code true} 静音, {@code false} 非静音
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStreamMuteByMusic(final boolean state) {
        return setStreamMute(AudioManager.STREAM_MUSIC, state);
    }

    /**
     * 设置通话声音静音状态
     * @param state {@code true} 静音, {@code false} 非静音
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStreamMuteByVoiceCall(final boolean state) {
        return setStreamMute(AudioManager.STREAM_VOICE_CALL, state);
    }

    /**
     * 设置系统声音静音状态
     * @param state {@code true} 静音, {@code false} 非静音
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStreamMuteBySystem(final boolean state) {
        return setStreamMute(AudioManager.STREAM_SYSTEM, state);
    }

    /**
     * 设置来电响铃静音状态
     * @param state {@code true} 静音, {@code false} 非静音
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStreamMuteByRing(final boolean state) {
        return setStreamMute(AudioManager.STREAM_RING, state);
    }

    /**
     * 设置闹钟声音静音状态
     * @param state {@code true} 静音, {@code false} 非静音
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStreamMuteByAlarm(final boolean state) {
        return setStreamMute(AudioManager.STREAM_ALARM, state);
    }

    /**
     * 设置通知声音静音状态
     * @param state {@code true} 静音, {@code false} 非静音
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStreamMuteByNotification(final boolean state) {
        return setStreamMute(AudioManager.STREAM_NOTIFICATION, state);
    }

    /**
     * 设置指定声音流静音状态
     * @param streamType 流类型
     * @param state      {@code true} 静音, {@code false} 非静音
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStreamMute(
            final int streamType,
            final boolean state
    ) {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.setStreamMute(streamType, state);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setStreamMute");
            }
        }
        return false;
    }

    // =======
    // = 模式 =
    // =======

    /**
     * 获取当前的音频模式
     * <pre>
     *     返回值有下述几种模式:
     *     MODE_NORMAL( 普通 )
     *     MODE_RINGTONE( 铃声 )
     *     MODE_IN_CALL( 打电话 )
     *     MODE_IN_COMMUNICATION( 通话 )
     * </pre>
     * @return 当前的音频模式
     */
    public static int getMode() {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                return audioManager.getMode();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getMode");
            }
        }
        return AudioManager.MODE_NORMAL;
    }

    /**
     * 设置当前的音频模式
     * <pre>
     *     有下述几种模式:
     *     MODE_NORMAL( 普通 )
     *     MODE_RINGTONE( 铃声 )
     *     MODE_IN_CALL( 打电话 )
     *     MODE_IN_COMMUNICATION( 通话 )
     * </pre>
     * @param mode 音频模式
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMode(final int mode) {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.setMode(mode);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setMode");
            }
        }
        return false;
    }

    // ===========
    // = 铃声模式 =
    // ===========

    /**
     * 获取当前的铃声模式
     * <pre>
     *     返回值有下述几种模式:
     *     RINGER_MODE_NORMAL( 普通 )
     *     RINGER_MODE_SILENT( 静音 )
     *     RINGER_MODE_VIBRATE( 震动 )
     * </pre>
     * @return 当前的铃声模式
     */
    public static int getRingerMode() {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                return audioManager.getRingerMode();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getRingerMode");
            }
        }
        return AudioManager.RINGER_MODE_NORMAL;
    }

    /**
     * 获取当前的铃声模式
     * @param ringerMode 铃声模式
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setRingerMode(final int ringerMode) {
        return setRingerMode(ringerMode, true);
    }

    /**
     * 获取当前的铃声模式
     * <pre>
     *     有下述几种模式:
     *     RINGER_MODE_NORMAL( 普通 )
     *     RINGER_MODE_SILENT( 静音 )
     *     RINGER_MODE_VIBRATE( 震动 )
     * </pre>
     * @param ringerMode 铃声模式
     * @param setting    如果没授权, 是否跳转到设置页面
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setRingerMode(
            final int ringerMode,
            final boolean setting
    ) {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                if (isDoNotDisturb(setting)) {
                    audioManager.setRingerMode(ringerMode);
                    return true;
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setRingerMode");
            }
        }
        return false;
    }

    // =

    /**
     * 设置静音模式 ( 静音, 且无振动 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean ringerSilent() {
        return setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    /**
     * 设置震动模式 ( 静音, 但有振动 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean ringerVibrate() {
        return setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    /**
     * 设置正常模式 ( 正常声音, 振动开关由 setVibrateSetting 决定 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean ringerNormal() {
        return setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    // =

    /**
     * 判断是否授权 Do not disturb 权限
     * <pre>
     *     授权 Do not disturb 权限, 才可进行音量操作
     * </pre>
     * @param setting 如果没授权, 是否跳转到设置页面
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDoNotDisturb(final boolean setting) {
        try {
            NotificationManager notificationManager = AppUtils.getNotificationManager();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                    && !notificationManager.isNotificationPolicyAccessGranted()) {
                if (setting) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    AppUtils.startActivity(intent);
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isDoNotDisturb");
        }
        return false;
    }

    // =

    /**
     * 设置是否打开扩音器 ( 扬声器 )
     * @param on {@code true} yes, {@code false} no
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setSpeakerphoneOn(final boolean on) {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.setSpeakerphoneOn(on);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setSpeakerphoneOn");
            }
        }
        return false;
    }

    /**
     * 设置是否让麦克风静音
     * @param on {@code true} yes, {@code false} no
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMicrophoneMute(final boolean on) {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.setMicrophoneMute(on);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setMicrophoneMute");
            }
        }
        return false;
    }

    /**
     * 判断是否打开扩音器 ( 扬声器 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSpeakerphoneOn() {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                return audioManager.isSpeakerphoneOn();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isSpeakerphoneOn");
            }
        }
        return false;
    }

    /**
     * 判断麦克风是否静音
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMicrophoneMute() {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                return audioManager.isMicrophoneMute();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isMicrophoneMute");
            }
        }
        return false;
    }

    /**
     * 判断是否有音乐处于活跃状态
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isMusicActive() {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                return audioManager.isMusicActive();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isMusicActive");
            }
        }
        return false;
    }

    /**
     * 判断是否插入了耳机
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isWiredHeadsetOn() {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                return audioManager.isWiredHeadsetOn();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isWiredHeadsetOn");
            }
        }
        return false;
    }

    /**
     * 检查蓝牙 A2DP 音频外设是否已连接
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isBluetoothA2dpOn() {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                return audioManager.isBluetoothA2dpOn();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isBluetoothA2dpOn");
            }
        }
        return false;
    }

    /**
     * 检查当前平台是否支持使用 SCO 的关闭调用用例
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isBluetoothScoAvailableOffCall() {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                return audioManager.isBluetoothScoAvailableOffCall();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isBluetoothScoAvailableOffCall");
            }
        }
        return false;
    }

    /**
     * 检查通信是否使用蓝牙 SCO
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isBluetoothScoOn() {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                return audioManager.isBluetoothScoOn();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isBluetoothScoOn");
            }
        }
        return false;
    }

    /**
     * 设置是否使用蓝牙 SCO 耳机进行通讯
     * @param on {@code true} yes, {@code false} no
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setBluetoothScoOn(final boolean on) {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.setBluetoothScoOn(on);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBluetoothScoOn");
            }
        }
        return false;
    }

    /**
     * 启动蓝牙 SCO 音频连接
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startBluetoothSco() {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.startBluetoothSco();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "startBluetoothSco");
            }
        }
        return false;
    }

    /**
     * 停止蓝牙 SCO 音频连接
     * @return {@code true} success, {@code false} fail
     */
    public static boolean stopBluetoothSco() {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.stopBluetoothSco();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "stopBluetoothSco");
            }
        }
        return false;
    }

    // =

    /**
     * 加载音效
     * @return {@code true} success, {@code false} fail
     */
    public static boolean loadSoundEffects() {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.loadSoundEffects();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "loadSoundEffects");
            }
        }
        return false;
    }

    /**
     * 卸载音效
     * @return {@code true} success, {@code false} fail
     */
    public static boolean unloadSoundEffects() {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.unloadSoundEffects();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "unloadSoundEffects");
            }
        }
        return false;
    }

    /**
     * 播放音效
     * @param effectType {@link AudioManager#FX_KEY_CLICK},
     *                   {@link AudioManager#FX_FOCUS_NAVIGATION_UP},
     *                   {@link AudioManager#FX_FOCUS_NAVIGATION_DOWN},
     *                   {@link AudioManager#FX_FOCUS_NAVIGATION_LEFT},
     *                   {@link AudioManager#FX_FOCUS_NAVIGATION_RIGHT},
     *                   {@link AudioManager#FX_KEYPRESS_STANDARD},
     *                   {@link AudioManager#FX_KEYPRESS_SPACEBAR},
     *                   {@link AudioManager#FX_KEYPRESS_DELETE},
     *                   {@link AudioManager#FX_KEYPRESS_RETURN},
     *                   {@link AudioManager#FX_KEYPRESS_INVALID},
     * @param volume     音量大小
     * @return {@code true} success, {@code false} fail
     */
    public static boolean playSoundEffect(
            final int effectType,
            final float volume
    ) {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.playSoundEffect(effectType, volume);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "playSoundEffect");
            }
        }
        return false;
    }

    // =

    /**
     * 放弃音频焦点, 使上一个焦点所有者 ( 如果有 ) 接收焦点
     * @param listener 焦点监听事件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean abandonAudioFocus(final AudioManager.OnAudioFocusChangeListener listener) {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.abandonAudioFocus(listener);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "abandonAudioFocus");
            }
        }
        return false;
    }

    /**
     * 调整最相关的流的音量, 或者给定的回退流
     * @param direction 调整参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean adjustSuggestedStreamVolume(final int direction) {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                audioManager.adjustSuggestedStreamVolume(direction, AudioManager.USE_DEFAULT_STREAM_TYPE, 0);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "adjustSuggestedStreamVolume");
            }
        }
        return false;
    }

    /**
     * 获取音频硬件指定 key 的参数值
     * @param keys Key
     * @return 音频硬件指定 key 的参数值
     */
    public static String getParameters(final String keys) {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                return audioManager.getParameters(keys);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getParameters");
            }
        }
        return null;
    }

    /**
     * 获取用户对振动类型的振动设置
     * @param vibrateType {@link AudioManager#VIBRATE_TYPE_NOTIFICATION} or {@link AudioManager#VIBRATE_TYPE_RINGER}
     * @return {@link AudioManager#VIBRATE_SETTING_ON}, {@link AudioManager#VIBRATE_SETTING_OFF}, {@link AudioManager#VIBRATE_SETTING_ONLY_SILENT}
     */
    public static int getVibrateSetting(final int vibrateType) {
        AudioManager audioManager = AppUtils.getAudioManager();
        if (audioManager != null) {
            try {
                return audioManager.getVibrateSetting(vibrateType);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getVibrateSetting");
            }
        }
        return -1;
    }
}