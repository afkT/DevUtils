package dev.utils.app.assist;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;

import dev.utils.LogPrintUtils;

/**
 * detail: 屏幕传感器(监听是否横竖屏)
 * @author Ttt
 */
public final class ScreenSensorAssist {

    // 日志 TAG
    private final String TAG = ScreenSensorAssist.class.getSimpleName();

    // ======================
    // = 重力传感器监听对象 =
    // ======================

    // 传感器管理对象
    private SensorManager sensorManager;
    // 重力传感器
    private Sensor sensor;
    // 重力传感器监听事件
    private OrientationSensorListener listener;

    // ================================================
    // = 重力传感器监听对象(改变方向后, 判断参数不同) =
    // ================================================

    // 传感器管理对象(切屏后)
    private SensorManager sensorManagerChange;
    // 重力传感器监听事件(切屏后)
    private OrientationSensorChangeListener listenerChange;

    // ========
    // = 常量 =
    // ========

    // 坐标索引常量
    private final int _DATA_X = 0;
    private final int _DATA_Y = 1;
    private final int _DATA_Z = 2;
    // 方向未知常量
    private final int ORIENTATION_UNKNOWN = -1;
    // 触发屏幕方向改变回调
    public static final int CHANGE_ORIENTATION_WHAT = 9919;

    // ========
    // = 变量 =
    // ========

    // 是否允许切屏
    private boolean allowChange = false;
    // 是否是竖屏
    private boolean portrait = true;
    // 回调操作
    private Handler handler;

    // 角度处理 Handler
    private Handler rotateHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_ORIENTATION_WHAT:
                    // 获取角度
                    int rotation = msg.arg1;
                    // =
                    LogPrintUtils.dTag(TAG, "当前角度: " + rotation);
                    // 判断角度
                    if (rotation > 45 && rotation < 135) { // 横屏 - 屏幕对着别人
                        LogPrintUtils.dTag(TAG, "切换成横屏 - 屏幕对着自己");
                        // =
                        if (portrait) {
                            portrait = false;
                            if (handler != null) {
                                Message vMsg = new Message();
                                vMsg.what = CHANGE_ORIENTATION_WHAT;
                                vMsg.arg1 = 1;
                                handler.sendMessage(vMsg);
                            }
                        }
                    } else if (rotation > 135 && rotation < 225) { // 竖屏 - 屏幕对着别人
                        LogPrintUtils.dTag(TAG, "切换成竖屏 - 屏幕对着别人");
                        // =
                        if (!portrait) {
                            portrait = true;
                            if (handler != null) {
                                Message vMsg = new Message();
                                vMsg.what = CHANGE_ORIENTATION_WHAT;
                                vMsg.arg1 = 2;
                                handler.sendMessage(vMsg);
                            }
                        }
                    } else if (rotation > 225 && rotation < 315) { // 横屏 - 屏幕对着自己
                        LogPrintUtils.dTag(TAG, "切换成横屏 - 屏幕对着自己");
                        // =
                        if (portrait) {
                            portrait = false;
                            if (handler != null) {
                                Message vMsg = new Message();
                                vMsg.what = CHANGE_ORIENTATION_WHAT;
                                vMsg.arg1 = 1;
                                handler.sendMessage(vMsg);
                            }
                        }
                    } else if ((rotation > 315 && rotation < 360) || (rotation > 0 && rotation < 45)) { // 竖屏 - 屏幕对着自己
                        LogPrintUtils.dTag(TAG, "切换成竖屏 - 屏幕对着自己");
                        // =
                        if (!portrait) {
                            portrait = true;
                            if (handler != null) {
                                Message vMsg = new Message();
                                vMsg.what = CHANGE_ORIENTATION_WHAT;
                                vMsg.arg1 = 2;
                                handler.sendMessage(vMsg);
                            }
                        }
                    } else {
                        LogPrintUtils.dTag(TAG, "其他角度: " + rotation);
                    }
                    break;
            }
        }
    };

    // =

    /**
     * 初始化操作
     * @param context
     * @param handler 回调Handler
     */
    private void init(final Context context, final Handler handler) {
        this.handler = handler;

        // 设置传感器
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 注册重力感应器,监听屏幕旋转
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        listener = new OrientationSensorListener();

        // 根据 旋转之后/点击全屏之后 两者方向一致,激活sm.
        sensorManagerChange = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        listenerChange = new OrientationSensorChangeListener();
    }

    /**
     * 开始监听
     * @param context
     */
    public void start(final Context context, final Handler handler) {
        allowChange = true;
        try {
            LogPrintUtils.dTag(TAG, "start orientation listener.");
            // 初始化操作
            init(context, handler);
            // 监听重力传感器
            sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "start");
        }
    }

    /**
     * 停止监听
     */
    public void stop() {
        allowChange = false;
        LogPrintUtils.dTag(TAG, "stop orientation listener.");
        try {
            sensorManager.unregisterListener(listener);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stop");
        }
        try {
            sensorManagerChange.unregisterListener(listenerChange);
        } catch (Exception e) {
        }
    }

    /**
     * 是否竖屏
     * @return {@code true} 竖屏, {@code false} 非竖屏
     */
    public boolean isPortrait() {
        return this.portrait;
    }

    /**
     * 是否允许切屏
     * @return {@code true} 允许, {@code false} 不允许
     */
    public boolean isAllowChange() {
        return this.allowChange;
    }

    // =

    /**
     * 重力传感器监听事件
     */
    class OrientationSensorListener implements SensorEventListener {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            int orientation = ORIENTATION_UNKNOWN;
            float X = -values[_DATA_X];
            float Y = -values[_DATA_Y];
            float Z = -values[_DATA_Z];
            float magnitude = X * X + Y * Y;
            // Don't trust the angle if the magnitude is small compared to the y value
            if (magnitude * 4 >= Z * Z) {
                // 屏幕旋转时
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                orientation = 90 - (int) Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }
            if (rotateHandler != null) {
                rotateHandler.obtainMessage(CHANGE_ORIENTATION_WHAT, orientation, 0).sendToTarget();
            }
        }
    }

    /**
     * 重力传感器监听事件(切屏后)
     */
    class OrientationSensorChangeListener implements SensorEventListener {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            int orientation = ORIENTATION_UNKNOWN;
            float X = -values[_DATA_X];
            float Y = -values[_DATA_Y];
            float Z = -values[_DATA_Z];
            float magnitude = X * X + Y * Y;
            // Don't trust the angle if the magnitude is small compared to the y value
            if (magnitude * 4 >= Z * Z) {
                // 屏幕旋转时
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                orientation = 90 - (int) Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }
            if (orientation > 225 && orientation < 315) {// 检测到当前实际是横屏
                if (!portrait) {
                    sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
                    sensorManagerChange.unregisterListener(listenerChange);
                }
            } else if ((orientation > 315 && orientation < 360) || (orientation > 0 && orientation < 45)) {// 检测到当前实际是竖屏
                if (portrait) {
                    sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
                    sensorManagerChange.unregisterListener(listenerChange);
                }
            }
        }
    }
}
